package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.*;

@Entity
public class Timeflip implements Persistable<String>, Serializable {


    private static final long serialVersionUID = 1L;


    @Id
    @Column(length = 100)
    private String macAddress;

    @OneToOne
    private User user;

    //List<int[]> history = new ArrayList<int[]>();

    @OneToOne
    private User createdUser;

    @ManyToOne
    private Raspberry raspberry;

    @Temporal(TemporalType.TIMESTAMP)
    private Date historyTime;

    @ElementCollection(targetClass = TaskEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "map_task_type")
    @Enumerated(EnumType.STRING)
    private Map<Integer, TaskEnum> tasks;




    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer, TaskEnum> getTasks() {
        return tasks;
    }

    public void setTasks(Map<Integer, TaskEnum> tasks) {

        this.tasks = tasks;
    }

    public void setTasksOnlyTasks(List<TaskEnum> tasks) {



        int taskCounter = 0;


       /* for(int i = 0; i < 12; i++){

            if(tasks.isEmpty()){
                return;
            }

            if(this.tasks.get(i) == null){
                this.tasks.put(i, tasks.get(taskCounter));
                System.out.println("Schleife" + this.tasks.get(i));
                tasks.remove(taskCounter);
                taskCounter++;
            }
        }
        setTasks(this.tasks);
*/

       if(this.tasks.isEmpty()){
           System.out.println("NUUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLL");
       }

       for(Integer key : this.tasks.keySet()){
           System.out.println(key);
       }
        for(Map.Entry<Integer, TaskEnum> entry : this.tasks.entrySet()) {

            System.out.println("In schleifeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        }

            /*if(entry.getValue() == null){
                System.out.println("Sind in if block" + tasks.get(i));
                entry.setValue(this.tasks.get(i));
                tasks.remove(i);
                i++;
            }
            System.out.println("Auserhalb if");
            if(tasks.isEmpty()){
                return;
            }
        }*/

    }

    public List<TaskEnum> getTaskValues(){
        System.out.println("Get Valuuuuuuuuuuues" + tasks.get(1));
        return new ArrayList<TaskEnum>(tasks.values());
    }

    /*public List<int[]> getHistory() {
        return history;
    }

    public void setHistory(List<int[]> history) {
        this.history = history;
    }*/

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }

    public Date getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(Date historyTime) {
        this.historyTime = historyTime;
    }



    @Override
    public String getId() {
        return getMacAddress();
    }

    @Override
    public boolean isNew() {
        return false;
    }
}

