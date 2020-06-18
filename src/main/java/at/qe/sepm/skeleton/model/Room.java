package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Room implements Persistable<String>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 100)
    private String roomNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne(optional = true)
    private User createUser;

    @OneToOne(mappedBy = "room")
    private Raspberry raspberry;

    private boolean equipped;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }

    public boolean isEquipped() {
        return !(raspberry == null);
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Room)) {
            return false;
        }
        final Room other = (Room) obj;
        return Objects.equals(this.roomNumber, other.roomNumber);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.roomNumber);
        return hash;
    }

    @Override
    public String getId() {
        return getRoomNumber();
    }

    @Override
    public boolean isNew() {
        return null == createDate;
    }
}
