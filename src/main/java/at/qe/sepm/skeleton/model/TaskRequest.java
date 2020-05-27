package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="taskrequest")
@DiscriminatorValue("1")
public class TaskRequest extends Request {
    private Date requestedDate;

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public int getDiscriminatorValue() {
        return 1;
    }
}
