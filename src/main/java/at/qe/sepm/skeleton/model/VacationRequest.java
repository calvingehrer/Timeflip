package at.qe.sepm.skeleton.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class VacationRequest extends Request {
    private Date requestedStartDate;

    private Date RequestedEndDate;

    public Date getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(Date requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Date getRequestedEndDate() {
        return RequestedEndDate;
    }

    public void setRequestedEndDate(Date requestedEndDate) {
        RequestedEndDate = requestedEndDate;
    }
}
