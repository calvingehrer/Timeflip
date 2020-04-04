package at.qe.sepm.skeleton.model;

public enum Intervall {
    DAILY,
    WEEKLY,
    MONTHLY,
    NONE;

    public static Intervall convertStringToIntervall(String intervall) {
        if (intervall.equals("DAILY")) { return DAILY; }
        if (intervall.equals("WEEKLY")) { return WEEKLY; }
        if (intervall.equals("MONTHLY")) { return MONTHLY; }
        return NONE;
    }
}
