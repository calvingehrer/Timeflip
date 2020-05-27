package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.MailRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;

import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
@Component
@Scope("application")
public class ScheduledMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    TaskService taskService;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private Logger<String, User> logger;

    /**
     * method to send mails with statistics from the last day
     */


    @Scheduled(cron = "* */5 * * * *", zone = "Europe/Vienna")
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

    public String generateStatisticsMessage(User user, Interval interval){
        StringBuilder message = new StringBuilder();
        HashMap<TaskEnum, Long> tasks = getUserTasks(user, interval);
        message.append("Statistics\n\n");

        for(Map.Entry<TaskEnum, Long> entry : tasks.entrySet()){
            message.append(entry.getKey().toString());
            message.append(": ");
            message.append("\t");
            message.append(entry.getValue().toString());
            message.append("\n");
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

        return  taskService.getUserTasksBetweenDates(user, start, end);
    }

    public HashMap<TaskEnum, Long> userTasksWeekly(User user) {
        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant start = calendar.toInstant();

        return  taskService.getUserTasksBetweenDates(user, start, end);
    }

    public HashMap<TaskEnum, Long> userTasksMonthly(User user) {
        Calendar calendar = getToday();
        calendar.add(Calendar.DATE, 1);
        Instant end = calendar.toInstant();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Instant start = calendar.toInstant();

        return  taskService.getUserTasksBetweenDates(user, start, end);
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
