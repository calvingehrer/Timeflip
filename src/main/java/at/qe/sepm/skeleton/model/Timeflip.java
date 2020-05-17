package at.qe.sepm.skeleton.model;

import org.assertj.core.data.TemporalOffset;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Timeflip implements Persistable<String>, Serializable {


    private static final long serialVersionUID = 1L;


    @Id
    @Column(length = 100)
    private String macAddress;

    @OneToOne
    private User user;

    @OneToOne
    private User createdUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne
    private Raspberry raspberry;

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

