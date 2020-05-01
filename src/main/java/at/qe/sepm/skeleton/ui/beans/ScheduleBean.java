package at.qe.sepm.skeleton.ui.beans;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.time.LocalDateTime;

public class ScheduleBean {
    private final ScheduleModel model;

    public ScheduleBean() {
        model = new ScheduleModel<ScheduleEvent>();
        DefaultScheduleEvent event = DefaultScheduleEvent.builder()
                .title("title")
                .startDate(LocalDateTime.of(2019, 7, 27, 12, 00))
                .endDate(LocalDateTime.of(2019, 7, 27, 12, 30))
                .build();

        model.addEvent(event);
    }

    public ScheduleModel getModel() {
        return model;
    }
}
