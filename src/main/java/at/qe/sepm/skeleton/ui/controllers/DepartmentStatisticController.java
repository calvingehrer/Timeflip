package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;


@Component
@Scope("view")
public class DepartmentStatisticController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private PieChartModel monthDepartmentModel;


    @PostConstruct
    public void init() {
        departmentTasksLastMonth();
    }

    public PieChartModel getMonthDepartmentModel() {
        return monthDepartmentModel;
    }

    public void setMonthDepartmentModel(PieChartModel monthDepartmentModel) {
        this.monthDepartmentModel = monthDepartmentModel;
    }


    public void departmentTasksLastMonth() {
        Calendar calendar = getLastMonthEnd();Instant end = calendar.toInstant();
        calendar.set(Calendar.DAY_OF_MONTH, 1);Instant start = calendar.toInstant();
        monthDepartmentModel = initDepartmentModel(monthDepartmentModel, "Monthly Stats", start, end);
    }

    public PieChartModel initDepartmentModel(PieChartModel model, String title, Instant start, Instant end) {
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getDepartmentTasksBetweenDates(sessionInfoBean.getCurrentUser().getDepartment(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    public Calendar getLastMonthEnd(){
        Calendar calendar = getToday();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE,-1);
        return calendar;
    }

    private PieChartModel getPieChartModel(PieChartModel model, String title, HashMap<TaskEnum, Long> tasks) {
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

    public Calendar getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }




}
