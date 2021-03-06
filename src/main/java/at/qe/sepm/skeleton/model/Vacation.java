package at.qe.sepm.skeleton.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.Instant;
import java.util.Objects;

/**
 * Entity representing Vacation.
 */

@Embeddable
public class Vacation implements Comparable<Vacation> {

    public final static long MAX_VACATION_DAYS_PER_YEAR = 25;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant start;

    private Instant end;

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacation vacation = (Vacation) o;
        return Objects.equals(start, vacation.start) &&
                Objects.equals(end, vacation.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public int compareTo(Vacation o) {
        return this.getEnd().compareTo(o.getEnd());
    }
}
