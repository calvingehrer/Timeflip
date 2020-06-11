package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity(name="taskrequest")
@DiscriminatorValue("1")
public class TaskRequest extends Request {
    private Instant requestedStartDate;

    private Instant requestedEndDate;

    private TaskEnum taskType;

    public Instant getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(Instant requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Instant getRequestedEndDate() {
        return requestedEndDate;
    }

    public void setRequestedEndDate(Instant requestedEndDate) {
        this.requestedEndDate = requestedEndDate;
    }

    public TaskEnum getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskEnum taskType) {
        this.taskType = taskType;
    }

    public int getDiscriminatorValue() {
        return 1;
    }
}
