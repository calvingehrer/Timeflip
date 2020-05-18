package at.qe.sepm.skeleton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration of available Tasks.
 *
 */
public enum TaskEnum {

    KONZEPTION,
    DESIGN,
    IMPLEMENTIERUNG,
    TESTEN,
    DOKUMENTATION,
    FEHLERMANAGEMENT,
    MEETING,
    KUNDENBESPRECHNUNG,
    FORTBILDUNG,
    PROJEKTMANAGEMENT,
    SONSTIGES,
    AUSZEIT;

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
