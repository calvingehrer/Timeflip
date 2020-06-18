package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Timeflip implements Persistable<String>, Serializable {


    private static final long serialVersionUID = 1L;


    @Id
    @Column(length = 100)
    private String macAddress;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
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
    private int battery;
    private TaskEnum facet1;
    private TaskEnum facet2;
    private TaskEnum facet3;
    private TaskEnum facet4;
    private TaskEnum facet5;
    private TaskEnum facet6;
    private TaskEnum facet7;
    private TaskEnum facet8;
    private TaskEnum facet9;
    private TaskEnum facet10;
    private TaskEnum facet11;
    private TaskEnum facet12;

    public Map<Integer, TaskEnum> getTasks() {
        return tasks;
    }

    public void setTasks(Map<Integer, TaskEnum> tasks) {
        this.tasks = tasks;
    }

    public List<TaskEnum> getTaskValues() {
        return new ArrayList<TaskEnum>(tasks.values());
    }

    @Override
    public String getId() {
        return getMacAddress();
    }

    @Override
    public boolean isNew() {
        return null == createDate;
    }

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

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public TaskEnum getFacet1() {
        return facet1;
    }

    public void setFacet1(TaskEnum facet1) {
        this.facet1 = facet1;
    }

    public TaskEnum getFacet2() {
        return facet2;
    }

    public void setFacet2(TaskEnum facet2) {
        this.facet2 = facet2;
    }

    public TaskEnum getFacet3() {
        return facet3;
    }

    public void setFacet3(TaskEnum facet3) {
        this.facet3 = facet3;
    }

    public TaskEnum getFacet4() {
        return facet4;
    }

    public void setFacet4(TaskEnum facet4) {
        this.facet4 = facet4;
    }

    public TaskEnum getFacet5() {
        return facet5;
    }

    public void setFacet5(TaskEnum facet5) {
        this.facet5 = facet5;
    }

    public TaskEnum getFacet6() {
        return facet6;
    }

    public void setFacet6(TaskEnum facet6) {
        this.facet6 = facet6;
    }

    public TaskEnum getFacet7() {
        return facet7;
    }

    public void setFacet7(TaskEnum facet7) {
        this.facet7 = facet7;
    }

    public TaskEnum getFacet8() {
        return facet8;
    }

    public void setFacet8(TaskEnum facet8) {
        this.facet8 = facet8;
    }

    public TaskEnum getFacet9() {
        return facet9;
    }

    public void setFacet9(TaskEnum facet9) {
        this.facet9 = facet9;
    }

    public TaskEnum getFacet10() {
        return facet10;
    }

    public void setFacet10(TaskEnum facet10) {
        this.facet10 = facet10;
    }

    public TaskEnum getFacet11() {
        return facet11;
    }

    public void setFacet11(TaskEnum facet11) {
        this.facet11 = facet11;
    }

    public TaskEnum getFacet12() {
        return facet12;
    }

    public void setFacet12(TaskEnum facet12) {
        this.facet12 = facet12;
    }

}

