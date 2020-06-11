package at.qe.sepm.skeleton.model;

/**
 * Enumeration of possible badges.
 *
 */

public enum BadgeEnum {

    WEEKLY_CODE_MONKEY ("Weekly Code Monkey"), // most time with Implementierung
    CREATIVE_MIND ("Creative Mind"), // most time with Design
    FRIEND_AND_HELPER ("Friend and Helper"), // most Time with Kundenbetreuung
    ALL_ROUNDER ("All Rounder"), // most different tasks
    NIGHT_OWL ("Night Owl"), // most time after 20:00
    WISDOM_SEEKER ("Wisdom Seeker")// most time with Fortblidung
    ;

    private final String description;

    BadgeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
