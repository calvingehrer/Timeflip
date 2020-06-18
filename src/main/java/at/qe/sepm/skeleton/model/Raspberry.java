package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Raspberry implements Persistable<String>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "raspberry_id", length = 100)
    private String raspberryId;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne(optional = true)
    private User createUser;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Room room;

    //@OneToMany(fetch = FetchType.EAGER)
    //private Collection<Timeflip> timeflips;


    public String getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(String raspberryId) {
        this.raspberryId = raspberryId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

/*
    public Collection<Timeflip> getTimeflips() {
        return timeflips;
    }

    public void setTimeflips(Collection<Timeflip> timeflips) {
        this.timeflips = timeflips;
    }*/

    @Override
    public String getId() {
        return getRaspberryId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Raspberry)) {
            return false;
        }
        final Raspberry other = (Raspberry) obj;
        return Objects.equals(this.raspberryId, other.raspberryId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.raspberryId);
        return hash;
    }

    @Override
    public boolean isNew() {
        return null == createDate;
    }
}
