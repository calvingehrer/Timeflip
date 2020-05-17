package at.qe.sepm.skeleton.model;

import org.assertj.core.data.TemporalOffset;
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

    @OneToOne
    private User createUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne
    private Raspberry raspberry;


    @ElementCollection(targetClass = TaskEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "map_task_type")
    @Enumerated(EnumType.STRING)
    private Map<Integer, TaskEnum> tasks = new HashMap<>();




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


    public Map<Integer, TaskEnum> getTasks() { return tasks; }

    public void setTasks(Map<Integer, TaskEnum> tasks) {
        this.tasks = tasks;
    }

    public List<TaskEnum> getTaskValues(){
        return new ArrayList<TaskEnum>(tasks.values());
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
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

