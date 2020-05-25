package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.MailRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;

@Service
public class ScheduledMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private Logger<String, User> logger;

    /**
     * method to send mails with statistics from the last day
     */


    @Scheduled(cron = "0 0 8 * * MON-FRI", zone = "Europe/Vienna")
    public void sendDailyStatistics() {
        mailRepository.findByInterval(Interval.DAILY)
                .stream()
                .forEach(user -> { mailService.sendEmailTo(user, "your daily stats", "default"); });


    }

    /**
     * method to send mails with statistics from the last week
     */

    @Scheduled(cron = "0 0 8 * * MON", zone = "Europe/Vienna")
    public void sendWeeklyStatistics () {
        mailRepository.findByInterval(Interval.WEEKLY)
                .stream()
                .forEach(user -> { mailService.sendEmailTo(user, "your weekly stats", "default"); });

    }

    /**
     * method to send mails with statistics from the last month
     */

    @Scheduled(cron = "0 0 8 1-7 * MON", zone = "Europe/Vienna")
    public void sendMonthlyStatistics () {
        mailRepository.findByInterval(Interval.MONTHLY)
                .stream()
                .forEach(user -> { mailService.sendEmailTo(user, "your monthly stats", "default"); });

    }
}
