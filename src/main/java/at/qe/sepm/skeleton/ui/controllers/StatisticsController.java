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
public class StatisticsController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    private PieChartModel pieModel1;
    private PieChartModel todayModel;

    @PostConstruct
    public void init() {
        createPieModel1();
        tasksDaily();
    }

    private void createPieModels() {
        createPieModel1();
        //createPieModel2();
        //createLivePieModel();
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel1.set("Brand 1", 540);
        pieModel1.set("Brand 2", 325);
        pieModel1.set("Brand 3", 702);
        pieModel1.set("Brand 4", 421);

        pieModel1.setTitle("Simple Pie");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }

    public PieChartModel getTodayModel() {
        return todayModel;
    }

    public void setTodayModel(PieChartModel todayModel) {
        this.todayModel = todayModel;
    }

    public void tasksDaily() {
        todayModel = new PieChartModel();

        HashMap<TaskEnum, Long> dailyTasks = taskService.findTodayTasks(sessionInfoBean.getCurrentUser());
        for (Map.Entry<TaskEnum, Long> pair: dailyTasks.entrySet()) {
            todayModel.set(pair.getKey().toString(), pair.getValue());
        }

        todayModel.setTitle("Simple Pie");
        todayModel.setLegendPosition("w");
        todayModel.setShadow(false);

    }

    public void tasksWeekly() {

    }

    public void tasksMonthly() {

    }
}
