package at.qe.sepm.skeleton.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("2")
public class VacationRequest extends Request {
    private Date requestedStartDate;

    private Date requestedEndDate;

    public Date getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(Date requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Date getRequestedEndDate() {
        return requestedEndDate;
    }

    public void setRequestedEndDate(Date requestedEndDate) {
        this.requestedEndDate = requestedEndDate;
    }

    public int getDiscriminatorValue() {
        return 2;
    }
}
