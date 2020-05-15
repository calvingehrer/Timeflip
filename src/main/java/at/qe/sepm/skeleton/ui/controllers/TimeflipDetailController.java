package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.services.TimeflipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@Component
@Scope("view")
public class TimeflipDetailController implements Serializable {


    @Autowired
    TimeflipService timeflipService;

    Timeflip timeflip;

    boolean design = false;
    boolean konzeption  = false;
    boolean implementierung  = false;
    boolean testen  = false;
    boolean dokumentation  = false;
    boolean fehlermanagement  = false;
    boolean meeting  = false;
    boolean kundenbesprechung  = false;
    boolean fortbildung = false;
    boolean projektmanagement = false;
    boolean sonstiges = false;
    boolean auszeit = false;


    private List<TaskEnum> tasks = new ArrayList<>();


    public Timeflip getTimeflip() {
        return timeflip;
    }

    public void setTimeflip(Timeflip timeflip) {
        this.timeflip = timeflip;
    }

    public boolean isDesign() {
        return design;
    }

    public void setDesign(boolean design) {
        this.design = design;
        if(!this.tasks.contains(TaskEnum.DESIGN) && design){
            this.tasks.add(TaskEnum.DESIGN);
        }

    }

    public boolean isImplementierung() {
        return implementierung;
    }

    public void setImplementierung(boolean implementierung) {
        this.implementierung = implementierung;
        if(!this.tasks.contains(TaskEnum.IMPLEMENTIERUNG) && implementierung){
            this.tasks.add(TaskEnum.IMPLEMENTIERUNG);
        }
    }

    public boolean isTesten() {
        return testen;
    }

    public void setTesten(boolean testen) {
        this.testen = testen;
        if(!this.tasks.contains(TaskEnum.TESTEN) && testen){
            this.tasks.add(TaskEnum.TESTEN);
        }
    }

    public boolean isDokumentation() {
        return dokumentation;
    }

    public void setDokumentation(boolean dokumentation) {
        this.dokumentation = dokumentation;
        if(!this.tasks.contains(TaskEnum.DOKUMENTATION) && dokumentation){
            this.tasks.add(TaskEnum.DOKUMENTATION);
        }
    }

    public boolean isFehlermanagement() {
        return fehlermanagement;
    }

    public void setFehlermanagement(boolean fehlermanagement) {
        this.fehlermanagement = fehlermanagement;
        if(!this.tasks.contains(TaskEnum.FEHLERMANAGEMENT) && fehlermanagement){
            this.tasks.add(TaskEnum.FEHLERMANAGEMENT);
        }
    }

    public boolean isMeeting() {
        return meeting;
    }

    public void setMeeting(boolean meeting) {
        this.meeting = meeting;
        if(!this.tasks.contains(TaskEnum.MEETING) && meeting){
            this.tasks.add(TaskEnum.MEETING);
        }
    }

    public boolean isKundenbesprechung() {
        return kundenbesprechung;
    }

    public void setKundenbesprechung(boolean kundenbesprechung) {
        this.kundenbesprechung = kundenbesprechung;
        if(!this.tasks.contains(TaskEnum.KUNDENBESPRECHNUNG) && kundenbesprechung){
            this.tasks.add(TaskEnum.KUNDENBESPRECHNUNG);
        }
    }

    public boolean isFortbildung() {
        return fortbildung;
    }

    public void setFortbildung(boolean fortbildung) {
        this.fortbildung = fortbildung;
        if(!this.tasks.contains(TaskEnum.FORTBILDUNG) && fortbildung){
            this.tasks.add(TaskEnum.FORTBILDUNG);
        }
    }

    public boolean isProjektmanagement() {
        return projektmanagement;
    }

    public void setProjektmanagement(boolean projektmanagement) {
        this.projektmanagement = projektmanagement;
        if(!this.tasks.contains(TaskEnum.PROJEKTMANAGEMENT) && projektmanagement){
            this.tasks.add(TaskEnum.PROJEKTMANAGEMENT);
        }
    }

    public boolean isSonstiges() {
        return sonstiges;
    }

    public void setSonstiges(boolean sonstiges) {
        this.sonstiges = sonstiges;
        if(!this.tasks.contains(TaskEnum.SONSTIGES) && sonstiges){
            this.tasks.add(TaskEnum.SONSTIGES);
        }
    }

    public boolean isAuszeit() {
        return auszeit;
    }

    public void setAuszeit(boolean auszeit) {
        this.auszeit = auszeit;
        if(!this.tasks.contains(TaskEnum.AUSZEIT) && auszeit){
            this.tasks.add(TaskEnum.AUSZEIT);
        }
    }

    public boolean isKonzeption() {
        return konzeption;
    }

    public void setKonzeption(boolean konzeption) {
        this.konzeption = konzeption;
        if(!this.tasks.contains(TaskEnum.KONZEPTION) && konzeption){
            this.tasks.add(TaskEnum.KONZEPTION);
        }


    }

    public List<TaskEnum> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEnum> tasks) {
        this.tasks = tasks;
    }

    public void doReloadTimeflip() {
        timeflip = timeflipService.loadTimeflip(timeflip.getMacAddress());
    }

    public void doSaveTimeflip(){
        timeflip = this.timeflipService.saveTimeflip(timeflip);
    }


    public void addTasks(){

        Map<Integer, TaskEnum> map = new HashMap<>();


        if(timeflip == null){
            return;
        }


        for(int i = 1; i <= 12; i++){
            map.put(i, null);
        }

        int taskCounter = tasks.size()-1;
        for(Map.Entry<Integer, TaskEnum> entry : map.entrySet()){
            if(taskCounter < 0){
                return;
            }
            if(entry.getValue() == null){
                entry.setValue(tasks.get(taskCounter));
                tasks.remove(taskCounter);
                taskCounter--;

            }
            timeflip.setTasks(map);
            doSaveTimeflip();
        }
    }






}
