package at.qe.sepm.skeleton.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class TimeConverter {

    public static Instant addTime(Instant instant, long minutes) {
        return instant.plus(minutes, ChronoUnit.MINUTES);
    }

    public static int getYear(Instant instant) {
        return instant.atZone(ZoneId.of("UTC")).getYear();
    }

    public static Instant beginOfYear(int year) {
        LocalDateTime beginOfYear = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        return beginOfYear.atZone(ZoneId.of("UTC")).toInstant();
    }

    public static Instant endOfYear(int year) {
        LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        return endOfYear.atZone(ZoneId.of("UTC")).toInstant();
    }
}
