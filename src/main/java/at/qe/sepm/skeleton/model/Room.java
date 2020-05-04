package at.qe.sepm.skeleton.model;

import at.qe.sepm.skeleton.services.RaspberryService;
import org.eclipse.jdt.internal.compiler.ast.NullLiteral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    //@OneToOne
    //private Raspberry raspberry;

    //@Autowired
    //private RaspberryService raspberryService = new RaspberryService();

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

/*
    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }


*/
    public boolean getEquipped() {
       /* List<String> rooms = new ArrayList<>();
        for(Raspberry raspberry : raspberryService.getAllRaspberries()){
            if(!rooms.contains(raspberry.getRoom())){
                rooms.add(raspberry.getRoom().getRoomNumber());
            }
        }
        return rooms.contains(this.roomNumber);*/
       return false;
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
        if (!Objects.equals(this.roomNumber, other.roomNumber)) {
            return false;
        }
        return true;
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
