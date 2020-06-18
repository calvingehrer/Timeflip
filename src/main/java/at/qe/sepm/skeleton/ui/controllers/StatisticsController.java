package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import at.qe.sepm.skeleton.utils.MessagesView;
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
    TaskService taskService;

    @Autowired
    TeamService teamService;

    @Autowired
    SessionInfoBean sessionInfoBean;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    private UserService userService;


    private PieChartModel todayUserModel;
    private PieChartModel weekUserModel;
    private PieChartModel monthUserModel;

    private PieChartModel weekTeamModel;
    private PieChartModel monthTeamModel;

    private PieChartModel monthDepartmentModel;

    private BarChartModel monthBarUserModel;
    private BarChartModel monthBarTeamModel;
    private BarChartModel monthBarDepartmentModel;

    private Date chosenDate;
    private String chosenTeam;

    private List<String> teamsOfDepartment;

    /**
     * Calls the functions to initialize the Charts
     */

    @PostConstruct
    public void init() {
        userTasksDaily();
        userTasksWeekly();
        userTasksMonthly();

        teamTasksLastMonth();

        if (sessionInfoBean.hasRole("EMPLOYEE")) {
            Calendar calendar = getToday();
            chosenDate = Date.from(calendar.toInstant());
        }
        if (sessionInfoBean.hasRole("TEAMLEADER")) {
            teamTasksLastWeek();
            Calendar calendar = getLastMonthEnd();
            chosenDate = Date.from(calendar.toInstant());
        }
        if (sessionInfoBean.hasRole("DEPARTMENTLEADER")) {
            departmentTasksLastMonth();
            Calendar calendar = getLastMonthEnd();
            chosenDate = Date.from(calendar.toInstant());
            List<Team> teams = getTeamsOfCurrentDepartment();
            if (!teams.isEmpty()) {
                teamsOfDepartment = new ArrayList<>();
                for (Team entry : teams) {
                    teamsOfDepartment.add(entry.getTeamName());
                }
                chosenTeam = teamsOfDepartment.get(0);
            }
        }
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

    public Date getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getChosenTeam() {
        return chosenTeam;
    }

    public void setChosenTeam(String chosenTeam) {
        this.chosenTeam = chosenTeam;
    }

    /**
     * sets the hours, minutes, seconds and milliseconds of a date to zero
     *
     * @param date to set
     */

    public static void setDayToBeginning(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    public List<String> getTeamsOfDepartment() {
        return teamsOfDepartment;
    }

    public void setTeamsOfDepartment(List<String> teamsOfDepartment) {
        this.teamsOfDepartment = teamsOfDepartment;
    }

    /**
     * Functions that initialize the Charts needed for Statistics
     */

    private void userTasksDaily() {
        Calendar calendar = getToday();

        Instant start = calendar.toInstant();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        todayUserModel = initUserModel("Daily Stats", start, end);

    }

    private void userTasksWeekly() {

        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekUserModel = initUserModel("Weekly Stats", start, end);
    }


    private void userTasksMonthly() {

        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthUserModel = initUserModel("Monthly Stats", start, end);
        monthBarUserModel = initBarUserModel(start, end);
    }

    private void teamTasksLastWeek() {

        Calendar calendar = getToday();
        calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -1);
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        weekTeamModel = initTeamModel("Weekly Stats", start, end);
    }

    private void teamTasksLastMonth() {

        Calendar calendar = getLastMonthEnd();
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthTeamModel = initTeamModel("Monthly Stats", start, end);
        monthBarTeamModel = initBarTeamModel(start, end);
    }

    private void departmentTasksLastMonth() {

        Calendar calendar = getLastMonthEnd();
        Instant end = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        monthDepartmentModel = initDepartmentModel(start, end);
        monthBarDepartmentModel = initBarDepartmentModel(start, end);
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
        setDayToBeginning(calendar);
        return calendar;
    }

    /**
     * function that uses date to set the instant start to the beginning of the week and end to the last day of the week
     *
     * @param date to set
     */
    private static Instant firstDayOfWeek(Calendar date) {
        Calendar dateForWeek = (Calendar) date.clone();
        dateForWeek.set(Calendar.DAY_OF_WEEK, dateForWeek.getFirstDayOfWeek());
        return dateForWeek.toInstant();
    }

    /**
     * Returns the instant of first last day of a month
     *
     * @param date to set
     * @return firstDay
     */
    private static Instant firstDayOfMonth(Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date.toInstant();
    }

    /**
     * Returns the instant of the last day of a month
     *
     * @param date to set
     * @return lastDay
     */

    private static Instant lastDayOfMonth(Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        return date.toInstant();
    }

    /**
     * turns Calendar into Date, needed for choosing date
     *
     * @param date to set
     * @return calendar
     */
    private static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private static Instant lastDayOfWeek(Calendar date) {
        Calendar dateForWeek = (Calendar) date.clone();
        dateForWeek.set(Calendar.DAY_OF_WEEK, dateForWeek.getFirstDayOfWeek());
        dateForWeek.add(Calendar.DATE, 7);
        return dateForWeek.toInstant();
    }

    List<Team> getTeamsOfCurrentDepartment() {
        return departmentService.getTeamsOfDepartment(userService.getAuthenticatedUser().getDepartment());
    }

    /**
     * Functions that take a model, title, start date and end date and return a full Model of the Tasks with the help
     * of a get...ChartModel function.
     *
     * @param title to set
     * @param start of date
     * @param end of date
     * @return BarChart or PieChart
     */

    private PieChartModel initUserModel(String title, Instant start, Instant end) {
        PieChartModel model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getUserTasksBetweenDates(userService.getAuthenticatedUser(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    private BarChartModel initBarUserModel(Instant start, Instant end) {
        BarChartModel model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getUserTasksBetweenDates(userService.getAuthenticatedUser(), start, end);
        return getBarChartModel(model, tasks);
    }

    private PieChartModel initTeamModel(String title, Instant start, Instant end) {
        PieChartModel model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(userService.getAuthenticatedUser().getTeam(), start, end);
        return getPieChartModel(model, title, tasks);
    }

    private PieChartModel initTeamModelByChosenTeam(Team team, Instant start, Instant end) {
        PieChartModel model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(team, start, end);
        return getPieChartModel(model, "Monthly Stats", tasks);
    }

    private BarChartModel initBarTeamModel(Instant start, Instant end) {
        BarChartModel model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(userService.getAuthenticatedUser().getTeam(), start, end);
        return getBarChartModel(model, tasks);
    }

    private BarChartModel initBarTeamModelByChosenTeam(Team team, Instant start, Instant end) {
        BarChartModel model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getTeamTasksBetweenDates(team, start, end);
        return getBarChartModel(model, tasks);
    }

    private PieChartModel initDepartmentModel(Instant start, Instant end) {
        PieChartModel model = new PieChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getDepartmentTasksBetweenDates(userService.getAuthenticatedUser().getDepartment(), start, end);
        return getPieChartModel(model, "Monthly Stats", tasks);
    }

    private BarChartModel initBarDepartmentModel(Instant start, Instant end) {
        BarChartModel model = new BarChartModel();
        HashMap<TaskEnum, Long> tasks = taskService.getDepartmentTasksBetweenDates(userService.getAuthenticatedUser().getDepartment(), start, end);
        return getBarChartModel(model, tasks);
    }

    /**
     * Takes a model, title and a HashMap of tasks as parameters and fills the model with a PieChart of the tasks.
     *
     * @param model to create
     * @param title of the model
     * @param tasks to add to the model
     * @return model
     */

    private PieChartModel getPieChartModel(PieChartModel model, String title, HashMap<TaskEnum, Long> tasks) {
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
     * @param model to create
     * @param tasks to add to the model
     * @return model
     */


    private BarChartModel getBarChartModel(BarChartModel model, HashMap<TaskEnum, Long> tasks) {
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


        model.setTitle("Monthly Stats");
        model.setBarWidth(100);
        model.setShadow(false);
        return model;
    }

    /**
     * function that rebuilds the User-Charts based on the selected Date
     */

    public void rebuildChartsUser() {
        Calendar date = toCalendar(chosenDate);

        if (date.after(getToday())) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else {
            setDayToBeginning(date);

            Instant start = date.toInstant();

            date.add(Calendar.DATE, 1);
            Instant end;
            end = date.toInstant();
            todayUserModel = initUserModel("Daily Stats", start, end);
            date.add(Calendar.DATE, -1);

            start = firstDayOfWeek(date);
            end = lastDayOfWeek(date);
            weekUserModel = initUserModel("Weekly Stats", start, end);

            start = firstDayOfMonth(date);
            end = lastDayOfMonth(date);
            monthUserModel = initUserModel("Monthly Stats", start, end);
            monthBarUserModel = initBarUserModel(start, end);
        }

    }

    /**
     * function that rebuilds the Team-Charts based on the selected Date
     */
    public void rebuildChartsTeam() {
        Calendar date = toCalendar(chosenDate);
        Calendar today = getToday();

        if (today.get(Calendar.MONTH) == date.get(Calendar.MONTH) && today.get(Calendar.YEAR) == date.get(Calendar.YEAR) && today.get(Calendar.WEEK_OF_MONTH) == date.get(Calendar.WEEK_OF_MONTH)) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else if (date.after(getToday())) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else {
            setDayToBeginning(date);

            Instant start = firstDayOfWeek(date);
            Instant end = lastDayOfWeek(date);
            weekTeamModel = initTeamModel("Weekly Stats", start, end);


            start = firstDayOfMonth(date);
            end = lastDayOfMonth(date);
            monthTeamModel = initTeamModel("Monthly Stats", start, end);
            monthBarTeamModel = initBarTeamModel(start, end);
        }
    }

    /**
     * function that rebuilds the Department-Charts on user basis, based on the selected Date
     */
    public void rebuildChartsDepartment() {
        Calendar date = toCalendar(chosenDate);
        Calendar today = getToday();

        if (today.get(Calendar.MONTH) == date.get(Calendar.MONTH) && today.get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else if (date.after(getToday())) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else {
            setDayToBeginning(date);

            Instant start = firstDayOfMonth(date);
            Instant end = lastDayOfMonth(date);

            monthDepartmentModel = initDepartmentModel(start, end);
            monthBarDepartmentModel = initBarDepartmentModel(start, end);
        }

    }

    /**
     * function that rebuilds the Department-Charts on team basis, based on the selected Date and team
     */
    public void rebuildChartsDepartmentTeamBasis() {
        Calendar date = toCalendar(chosenDate);
        Calendar today = getToday();

        List<Team> teams = teamService.getAllTeamsByTeamName(chosenTeam);
        Team team = teams.get(0);

        if (today.get(Calendar.MONTH) == date.get(Calendar.MONTH) && today.get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else if (date.after(getToday())) {
            MessagesView.warnMessage("date selection", "You can't select this date");
        } else {
            setDayToBeginning(date);

            Instant start = firstDayOfMonth(date);
            Instant end = lastDayOfMonth(date);

            monthTeamModel = initTeamModelByChosenTeam(team, start, end);
            monthBarTeamModel = initBarTeamModelByChosenTeam(team, start, end);
        }

    }


}
