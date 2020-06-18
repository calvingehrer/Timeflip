package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TimeflipService;
import at.qe.sepm.skeleton.utils.MessagesView;
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

    User user;

    private List<TaskEnum> taskList = Arrays.asList(TaskEnum.values());
    private ArrayList<Integer> facetNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    private Map<Integer, TaskEnum> tasks = new HashMap<>();

    public void saveTasks(){

        if(timeflip == null){
            return;
        }

            doSaveTimeflip();

    }

    public Timeflip getTimeflip() {
        return timeflip;
    }

    public void setTimeflip(Timeflip timeflip) {
        this.timeflip = timeflip;
    }

    public void doReloadTimeflip() {
        timeflip = timeflipService.loadTimeflip(timeflip.getMacAddress());
    }

    public void doSaveTimeflip(){
        timeflip = this.timeflipService.saveTimeflip(timeflip);
    }

    public void doDeleteTimeflip() {
        this.timeflipService.deleteTimeflip(timeflip);
        timeflip = null;
        MessagesView.successMessage("timeflip deletion","successfully deleted");

    }


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public void addUser(){
        this.timeflip.setUser(user);
        doSaveTimeflip();
    }


    public void setTasks(Map<Integer, TaskEnum> tasks) {
        this.tasks = tasks;
    }

    public List<TaskEnum> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskEnum> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<Integer> getFacetNumbers() {
        return facetNumbers;
    }

    public void setFacetNumbers(ArrayList<Integer> facetNumbers) {
        this.facetNumbers = facetNumbers;
    }
}
