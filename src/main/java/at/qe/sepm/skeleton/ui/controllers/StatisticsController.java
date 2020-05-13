package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Component
@Scope("view")
public class StatisticsController implements Serializable {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private PieChartModel todayModel;
    private PieChartModel weekModel;

    @PostConstruct
    public void init() {
        tasksDaily();
        tasksWeekly();
    }


    public PieChartModel getTodayModel() {
        return todayModel;
    }

    public void setTodayModel(PieChartModel todayModel) {
        this.todayModel = todayModel;
    }

    public PieChartModel getWeekModel() {
        return weekModel;
    }

    public void setWeekModel(PieChartModel weekModel) {
        this.weekModel = weekModel;
    }

    public void tasksDaily() {
        Calendar calendar = getToday();

        Instant start = calendar.toInstant();
        calendar.add(Calendar.DATE,1);
        Instant end = calendar.toInstant();

        todayModel = initModel(todayModel, "Daily Stats", start, end);

    }

    public void tasksWeekly() {
        Calendar calendar = getToday();
        Instant end = calendar.toInstant();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekModel = initModel(weekModel, "Weekly Stats", start, end);
    }


    public void tasksMonthly() {

    }

    public Calendar getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public PieChartModel initModel(PieChartModel model, String title, Instant start, Instant end) {
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTasksBetweenDates(sessionInfoBean.getCurrentUser(), start, end);
        if (tasks.isEmpty()) {
            model.set("Keine Angaben", 100);
        }
        else {
            for (Map.Entry<TaskEnum, Long> pair : tasks.entrySet()) {
                model.set(pair.getKey().toString(), pair.getValue());
            }
        }

        model.setTitle(title);
        model.setLegendPosition("w");
        model.setShadow(false);
        return model;
    }
}
