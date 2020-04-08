package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Intervall;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    private MailRepository mailRepository;

    @Scheduled(cron = "0 0 8 * * *", zone = "Europe/Vienna")
    public void sendDailyStatistics () {
        for (User u: mailRepository.findByInterval(Intervall.DAILY)) {
            mailService.sendEmailTo(u, "your stats", "statistics");
        }
    }

    @Scheduled(cron = "0 0 8 * * MON", zone = "Europe/Vienna")
    public void sendWeeklyStatistics () {
        for (User u: mailRepository.findByInterval(Intervall.WEEKLY)) {
            mailService.sendEmailTo(u, "your stats", "statistics");
        }
    }

    @Scheduled(cron = "0 0 8 1-7 * MON", zone = "Europe/Vienna")
    public void sendMonthlyStatistics () {
        for (User u: mailRepository.findByInterval(Intervall.MONTHLY)) {
            mailService.sendEmailTo(u, "your stats", "statistics");
        }
    }
}
