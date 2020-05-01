package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Timeflip implements Persistable<String>, Serializable{


    private static final long serialVersionUID = 1L;


    @Id
    @Column(length = 100)
    private String macAddress;

    @OneToOne
    private User user;

    //List<int[]> history = new ArrayList<int[]>();

    @OneToOne
    private User createdUser;

    //private RaspberryPi raspberryPi;

    @Temporal(TemporalType.TIMESTAMP)
    private Date historyTime;




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

/*public RaspberryPi getRaspberryPi() {
    return raspberryPi;
}

public void setRaspberryPi(RaspberryPi raspberryPi) {
    this.raspberryPi = raspberryPi;
}*/

    public Date getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(Date historyTime) {
        this.historyTime = historyTime;
    }


    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}

