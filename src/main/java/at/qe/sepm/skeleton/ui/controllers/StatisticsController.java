package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.primefaces.model.chart.*;
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
    CurrentUserBean currentUserBean;

    private PieChartModel todayUserModel;
    private PieChartModel weekUserModel;
    private PieChartModel monthUserModel;

    private PieChartModel weekTeamModel;
    private PieChartModel monthTeamModel;

    private PieChartModel monthDepartmentModel;

    private BarChartModel monthBarUserModel;
    private BarChartModel monthBarTeamModel;
    private BarChartModel monthBarDepartmentModel;

    private Instant chosenDate;

    /**
     * Calls the functions to initialize the Charts
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
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

    public BarChartModel getMonthBarUserModel() {
        return monthBarUserModel;
    }

    public void setMonthBarUserModel(BarChartModel monthBarUserModel) {
        this.monthBarUserModel = monthBarUserModel;
    }

    public BarChartModel getMonthBarTeamModel() {
        return monthBarTeamModel;
    }

    public void setMonthBarTeamModel(BarChartModel monthBarTeamModel) {
        this.monthBarTeamModel = monthBarTeamModel;
    }

    public BarChartModel getMonthBarDepartmentModel() {
        return monthBarDepartmentModel;
    }

    public void setMonthBarDepartmentModel(BarChartModel monthBarDepartmentModel) {
        this.monthBarDepartmentModel = monthBarDepartmentModel;
    }


    /**
     * Functions that initialize the Charts needed for Statistics
     */

    private void userTasksDaily() {
        Calendar calendar = getToday();

        Instant start = calendar.toInstant();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        todayUserModel = initUserModel(todayUserModel, "Daily Stats", start, end);

    }

    private void userTasksWeekly() {

        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekUserModel = initUserModel(weekUserModel, "Weekly Stats", start, end);
    }


    private void userTasksMonthly() {

        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthUserModel = initUserModel(monthUserModel, "Monthly Stats", start, end);
        monthBarUserModel = initBarUserModel(monthBarUserModel, "Monthly Stats", start, end);
    }

    private void teamTasksLastWeek() {

        Calendar calendar = getToday();
        calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekTeamModel = initTeamModel(weekTeamModel, "Weekly Stats", start, end);
    }

    private void teamTasksLastMonth() {

        Calendar calendar = getLastMonthEnd();
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthTeamModel = initTeamModel(monthTeamModel, "Monthly Stats", start, end);
        monthBarTeamModel = initBarTeamModel(monthBarTeamModel, "Monthly Stats", start, end);
    }

    private void departmentTasksLastMonth() {

        Calendar calendar = getLastMonthEnd();
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthDepartmentModel = initDepartmentModel(monthDepartmentModel, "Monthly Stats", start, end);
        monthBarDepartmentModel = initBarDepartmentModel(monthBarDepartmentModel, "Monthly Stats", start, end);
    }

    /**
     * creates a Calendar with the last date from last month
     *
     * @return Calendar
     */

    private Calendar getLastMonthEnd() {
        Calendar calendar = getToday();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar;
    }

    /**
     * creates and returns a Calendar with the current date.
     *
     * @return Calendar
     */

    private Calendar getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Functions that take a model, title, start date and end date and return a full Model of the Tasks with the help
     * of a get...ChartModel function.
     *
     * @param model
     * @param title
     * @param start
     * @param end
     * @return BarChart or PieChart
     */

    private PieChartModel initUserModel(PieChartModel model, String title, Instant start, Instant end){
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getUserTasksBetweenDates(currentUserBean.getCurrentUser(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    private BarChartModel initBarUserModel(BarChartModel model, String title, Instant start, Instant end){
        model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getUserTasksBetweenDates(currentUserBean.getCurrentUser(), start, end);
        return getBarChartModel(model, title, tasks);
    }

    private PieChartModel initTeamModel(PieChartModel model, String title, Instant start, Instant end){
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(currentUserBean.getCurrentUser().getTeam(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    private BarChartModel initBarTeamModel(BarChartModel model, String title, Instant start, Instant end){
        model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(currentUserBean.getCurrentUser().getTeam(), start, end);
        return getBarChartModel(model, title, tasks);
    }

    private PieChartModel initDepartmentModel(PieChartModel model, String title, Instant start, Instant end){
        model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getDepartmentTasksBetweenDates(currentUserBean.getCurrentUser().getDepartment(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    private BarChartModel initBarDepartmentModel(BarChartModel model, String title, Instant start, Instant end){
        model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getDepartmentTasksBetweenDates(currentUserBean.getCurrentUser().getDepartment(), start, end);
        return getBarChartModel(model, title, tasks);
    }

    /**
     * Takes a model, title and a HashMap of tasks as parameters and fills the model with a PieChart of the tasks.
     *
     * @param model
     * @param title
     * @param tasks
     * @return model
     */

    private PieChartModel getPieChartModel (PieChartModel model, String title, HashMap <TaskEnum, Long> tasks){
        if (tasks.isEmpty()) {
            model.set("No Data", 100);
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

    /**
     * Takes a model, title and a HashMap of tasks as parameters and fills the model with a BarChart of the tasks.
     *
     * @param model
     * @param title
     * @param tasks
     * @return model
     */


    private BarChartModel getBarChartModel (BarChartModel model, String title, HashMap <TaskEnum, Long> tasks){
        if (tasks.isEmpty()) {
            ChartSeries series = new ChartSeries();
            series.set("No Data", 0);
            model.addSeries(series);
        } else {
            ChartSeries series = new ChartSeries();
            for (Map.Entry<TaskEnum, Long> pair : tasks.entrySet()) {
                series.set(pair.getKey().toString(), pair.getValue());
            }
            model.addSeries(series);
        }

        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setLabel("Tasks");

        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Minutes");


        model.setTitle(title);
        model.setBarWidth(100);
        model.setShadow(false);
        return model;
    }


}
