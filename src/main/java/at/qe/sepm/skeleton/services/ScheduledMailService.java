package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.MailRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;


import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * source: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Scheduled.html
 * class to send scheduled statistics per mail
 * either daily, weekly or monthly
 */

@Component
public class ScheduledMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private MailRepository mailRepository;

    /**
     * method to send mails with statistics from the last day
     */


    @Scheduled(cron = "0 0 8 * * MON-FRI", zone = "Europe/Vienna")
    public void sendDailyStatistics () {
        for (User u: mailRepository.findByInterval(Interval.DAILY)) {
            mailService.sendEmailTo(u, "your daily stats", generateStatisticsMessage(u, Interval.DAILY));
        }
    }


    /**
     * method to send mails with statistics from the last week
     */

    @Scheduled(cron = "0 0 8 * * MON", zone = "Europe/Vienna")
    public void sendWeeklyStatistics () {
        for (User u: mailRepository.findByInterval(Interval.WEEKLY)) {
            mailService.sendEmailTo(u, "your weekly stats", generateStatisticsMessage(u, Interval.WEEKLY));
        }
    }

    /**
     * method to send mails with statistics from the last month
     */

    @Scheduled(cron = "0 0 8 1-7 * MON", zone = "Europe/Vienna")
    public void sendMonthlyStatistics () {
        for (User u: mailRepository.findByInterval(Interval.MONTHLY)) {
            mailService.sendEmailTo(u, "your monthly stats", generateStatisticsMessage(u, Interval.MONTHLY));
        }
    }

    @Scheduled(cron = "0 0 8 * * MON-FRI", zone = "Europe/Vienna")
    public void sendSynchronisationReminder () {
        Instant currentTime = Instant.now();
        for (User u: userService.getAllUsers()) {
            List<Task> lastThreeDays = taskRepository.findUserTasksBetweenDates(u, currentTime.minus(3, ChronoUnit.DAYS), currentTime);
            if(lastThreeDays.isEmpty()){
                if(u.getEmail() != null && !u.getEmail().isEmpty()){
                    mailService.sendEmailTo(u, "Synchronisation Reminder",
                            "Hello " + u.getFirstName() + "! \nPlease check your TimeFlip Device. The last synchronisation was more than 3 days ago.\nCheers,\nTimeFlipper Team");
                }
            }
        }
    }


    public String generateStatisticsMessage(User user, Interval interval){
        StringBuilder message = new StringBuilder();
        HashMap<TaskEnum, Long> tasks = getUserTasks(user, interval);
        message.append("Statistics\n\n");

        for(Map.Entry<TaskEnum, Long> entry : tasks.entrySet()){
            message.append(entry.getKey().toString());
            message.append(": ");
            message.append("\t");
            message.append(entry.getValue().toString());
            message.append("min\n");
        }
        return message.toString();
    }

    public HashMap<TaskEnum, Long> getUserTasks(User user, Interval interval){
        switch (interval){
            case DAILY: return userTasksDaily(user);
            case WEEKLY: return userTasksWeekly(user);
            case MONTHLY: return userTasksMonthly(user);
            default: return null;
        }
    }

    public HashMap<TaskEnum, Long> userTasksDaily(User user) {
        Calendar calendar = getToday();
        Instant start = calendar.toInstant();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();

        return  getUserTasksBetweenDates(user, start, end);
    }

    public HashMap<TaskEnum, Long> userTasksWeekly(User user) {
        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        return  getUserTasksBetweenDates(user, start, end);
    }

    public HashMap<TaskEnum, Long> userTasksMonthly(User user) {
        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        return  getUserTasksBetweenDates(user, start, end);
    }

    public HashMap<TaskEnum, Long> getUserTasksBetweenDates(User user, Instant start, Instant end) {

        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findUserTasksBetweenDates(user, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

    private HashMap<TaskEnum, Long> fillTaskList(HashMap<TaskEnum, Long> dailyTasks, List<Task> tasks) {
        tasks.forEach(t -> {
                    long d = getDuration(t);
                    if (dailyTasks.containsKey(t.getTask())) {
                        dailyTasks.put(t.getTask(), dailyTasks.get(t.getTask()) + d);
                    } else {
                        dailyTasks.put(t.getTask(), d);
                    }
                }
        );

        return dailyTasks;
    }

    public long getDuration(Task task) {
        long duration = Duration.between(task.getStartTime(), task.getEndTime()).toMinutes();
        return duration;
    }

    public Calendar getToday () {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
}
