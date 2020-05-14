package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Request implements Persistable<Long>, Serializable {

    private static final long serialVersionUID = 1543543567124567565L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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

    private Date requestedDate;

    private String description;

    @Override
    public Long getId() {
        return this.Id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    @Override
    public boolean isNew() { return null == createDate; }
}
