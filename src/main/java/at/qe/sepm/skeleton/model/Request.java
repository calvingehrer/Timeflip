package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "request_type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Request implements Persistable<Long>, Serializable {
    private static final long serialVersionUID = 1543543567124567565L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long Id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "requester")
    private User requester;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_handler_tl")
    private User requestHandlerTeamLeader;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_handler_dl")
    private User requestHandlerDepartmentLeader;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestEnum status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


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

    public User getRequestHandlerTeamLeader() {
        return requestHandlerTeamLeader;
    }

    public void setRequestHandlerTeamLeader(User requestHandlerTeamLeader) {
        this.requestHandlerTeamLeader = requestHandlerTeamLeader;
    }

    public User getRequestHandlerDepartmentLeader() {
        return requestHandlerDepartmentLeader;
    }

    public void setRequestHandlerDepartmentLeader(User requestHandlerDepartmentLeader) {
        this.requestHandlerDepartmentLeader = requestHandlerDepartmentLeader;
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

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean isNew() {
        return null == createDate;
    }

    public abstract int getDiscriminatorValue();
}
