package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.MailRepository;
import at.qe.sepm.skeleton.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    private MailRepository mailRepository;

    @Scheduled(cron = "0 0 8 * * MON-FRI", zone = "Europe/Vienna")
    public void sendDailyStatistics () {
        for (User u: mailRepository.findByInterval(Interval.DAILY)) {
            mailService.sendEmailTo(u, "your daily stats", "default");
        }
    }

    @Scheduled(cron = "0 0 8 * * MON", zone = "Europe/Vienna")
    public void sendWeeklyStatistics () {
        for (User u: mailRepository.findByInterval(Interval.WEEKLY)) {
            mailService.sendEmailTo(u, "your weekly stats", "default");
        }
    }

    @Scheduled(cron = "0 0 8 1-7 * MON", zone = "Europe/Vienna")
    public void sendMonthlyStatistics () {
        for (User u: mailRepository.findByInterval(Interval.MONTHLY)) {
            mailService.sendEmailTo(u, "your monthly stats", "default");
        }
    }
}
