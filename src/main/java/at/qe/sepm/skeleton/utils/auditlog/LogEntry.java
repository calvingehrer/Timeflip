package at.qe.sepm.skeleton.utils.auditlog;

/**
 * Represents a persistent class which saves an audit log entry with:
 */

import at.qe.sepm.skeleton.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LogEnum logActionType;

    @Column(length = 250)
    private String message;

    @ManyToOne(optional = false)
    private User changingUser;

    @Column(nullable = false)
    private Instant logDate;

    User user = new User();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogEnum getLogActionType() {
        return logActionType;
    }

    public void setLogActionType(LogEnum logActionType) {
        this.logActionType = logActionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getChangingUser() {
        return changingUser;
    }

    public void setChangingUser(User changingUser) {
        this.changingUser = changingUser;
    }

    public void setChangingUser2(String changingUser) {
        this.changingUser = user;
    }

    public Instant getLogDate() {
        return logDate;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
    }


    public boolean isNew() {
        return id == null;
    }
}
