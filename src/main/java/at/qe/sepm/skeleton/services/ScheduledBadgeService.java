package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;

@Service
public class ScheduledBadgeService {

    @Autowired
    BadgeService badgeService;

    @Scheduled(cron = "0 0 8 * * MON", zone = "Europe/Vienna")
    public void awardBadges () {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -1);
        Instant weekEnd = calendar.toInstant();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Instant weekStart = calendar.toInstant();

        badgeService.evaluateWeeklyBadges(weekStart, weekEnd);
    }
}
