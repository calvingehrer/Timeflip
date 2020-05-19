package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledBadgeService {

    @Scheduled(cron = "0 0 8 * * MON", zone = "Europe/Vienna")
    public void awardBadges () {

    }
}
