package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
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
    private UserService userService;

    private PieChartModel todayUserModel;
    private PieChartModel weekUserModel;
    private PieChartModel monthUserModel;

    private PieChartModel weekTeamModel;
    private PieChartModel monthTeamModel;

    private PieChartModel monthDepartmentModel;

    private User currentUser;

    @PostConstruct
    public void init() {
        this.setCurrentUser(userService.getAuthenticatedUser());
        userTasksDaily();
        userTasksWeekly();
        userTasksMonthly();

        teamTasksLastWeek();
        teamTasksLastMonth();

        departmentTasksLastMonth();
    }


    public PieChartModel getTodayUserModel() {
        return todayUserModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setTodayUserModel(PieChartModel todayUserModel) {
        this.todayUserModel = todayUserModel;
    }

    public PieChartModel getWeekUserModel() {
        return weekUserModel;
    }

    public void setWeekUserModel(PieChartModel weekUserModel) {
        this.weekUserModel = weekUserModel;
    }

    public PieChartModel getMonthUserModel() {
        return monthUserModel;
    }

    public void setMonthUserModel(PieChartModel monthUserModel) {
        this.monthUserModel = monthUserModel;
    }

    public PieChartModel getWeekTeamModel() {
        return weekTeamModel;
    }

    public void setWeekTeamModel(PieChartModel weekTeamModel) {
        this.weekTeamModel = weekTeamModel;
    }

    public PieChartModel getMonthTeamModel() {
        return monthTeamModel;
    }

    public void setMonthTeamModel(PieChartModel monthTeamModel) {
        this.monthTeamModel = monthTeamModel;
    }

    public PieChartModel getMonthDepartmentModel() {
        return monthDepartmentModel;
    }

    public void setMonthDepartmentModel(PieChartModel monthDepartmentModel) {
        this.monthDepartmentModel = monthDepartmentModel;
    }

    public void userTasksDaily() {
        Calendar calendar = getToday();

        Instant start = calendar.toInstant();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        todayUserModel = initUserModel(todayUserModel, "Daily Stats", start, end);

    }

    public void userTasksWeekly () {

        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekUserModel = initUserModel(weekUserModel, "Weekly Stats", start, end);
    }


    public void userTasksMonthly () {

        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthUserModel = initUserModel(monthUserModel, "Monthly Stats", start, end);
    }

    public void teamTasksLastWeek () {

        Calendar calendar = getToday();
        calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekTeamModel = initTeamModel(weekTeamModel, "Weekly Stats", start, end);
    }

    public void teamTasksLastMonth () {

        Calendar calendar = getLastMonthEnd();
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthTeamModel = initTeamModel(monthTeamModel, "Monthly Stats", start, end);
    }

    public void departmentTasksLastMonth () {

        Calendar calendar = getLastMonthEnd();
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthDepartmentModel = initDepartmentModel(monthDepartmentModel, "Monthly Stats", start, end);
    }

    public Calendar getLastMonthEnd () {
        Calendar calendar = getToday();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar;
    }

    public Calendar getToday () {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public PieChartModel initUserModel (PieChartModel model, String title, Instant start, Instant end){
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getUserTasksBetweenDates(getCurrentUser(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    public PieChartModel initTeamModel (PieChartModel model, String title, Instant start, Instant end){
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(getCurrentUser().getTeam(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    public PieChartModel initDepartmentModel (PieChartModel model, String title, Instant start, Instant end){
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getDepartmentTasksBetweenDates(getCurrentUser().getDepartment(), start, end);
        return getPieChartModel(model, title, tasks);
    }


    private PieChartModel getPieChartModel (PieChartModel model, String title, HashMap <TaskEnum, Long> tasks){
        if (tasks.isEmpty()) {
            model.set("Keine Angaben", 100);
        } else {
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
