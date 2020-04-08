package at.qe.sepm.skeleton.model;

public enum Interval {
    DAILY,
    WEEKLY,
    MONTHLY,
    NONE;

    public static Interval convertStringToIntervall(String intervall) {
        if (intervall.equals("DAILY")) { return DAILY; }
        if (intervall.equals("WEEKLY")) { return WEEKLY; }
        if (intervall.equals("MONTHLY")) { return MONTHLY; }
        return NONE;
    }
}
