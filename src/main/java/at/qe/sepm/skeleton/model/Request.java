package at.qe.sepm.skeleton.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Request implements Persistable<String>, Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="request_id")
    private String requestId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="requester")
    private User requester;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="request_handler")
    private User requestHandler;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private RequestEnum status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private String description;

    public String getId() {
        return requestId;
    }

    public void setId(String requestId) {
        this.requestId = requestId;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(User requestHandler) {
        this.requestHandler = requestHandler;
    }

    public RequestEnum getStatus() {
        return status;
    }

    public void setStatus(RequestEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isNew() { return null == createDate; }
}
