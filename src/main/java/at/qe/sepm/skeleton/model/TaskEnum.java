package at.qe.sepm.skeleton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration of available Tasks.
 */
public enum TaskEnum {

    KONZEPTION("Konzeption"),
    DESIGN("Design"),
    IMPLEMENTIERUNG("Implementierung"),
    TESTEN("Testen"),
    DOKUMENTATION("Dokumentation"),
    FEHLERMANAGEMENT("Fehlermanagement"),
    MEETING("Meeting"),
    KUNDENBESPRECHUNG("Kundenbesprechung"),
    FORTBILDUNG("Fortbildung"),
    PROJEKTMANAGEMENT("Projektmanagement"),
    SONSTIGES("Sonstiges"),
    AUSZEIT("Auszeit");

    private final String description;

    TaskEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }

    public static List<String> getAllTasks() {
        List<String> tasks = new ArrayList();
        tasks.add("KONZEPTION");
        tasks.add("DESIGN");
        tasks.add("IMPLEMENTIERUNG");
        tasks.add("TESTEN");
        tasks.add("DOKUMENTATION");
        tasks.add("FEHLERMANAGEMENT");
        tasks.add("MEETING");
        tasks.add("KUNDENBESPRECHUNG");
        tasks.add("FORTBILDUNG");
        tasks.add("PROJEKTMANAGEMENT");
        tasks.add("SONSTIGES");
        tasks.add("AUSZEIT");
        return tasks;
    }

}
