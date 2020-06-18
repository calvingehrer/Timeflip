package at.qe.sepm.skeleton.ui.beans;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.TaskEnum;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * a class that contains all the complete methods needed for <p:autocomplete></p:autocomplete>
 */

@Component
@Scope("request")
public class CompleteMethodBean {
    /**
     * method to complete the tasks on the ui so it is easier to choose from them
     *
     * @param query
     * @return
     */

    public List<String> completeTask(String query) {
        String upperQuery = query.toUpperCase();
        return TaskEnum.getAllTasks().stream().filter(a -> a.contains(upperQuery)).collect(Collectors.toList());
    }

    /**
     * method to complete the intervals
     *
     * @param query
     * @return
     */

    public List<String> completeIntervall(String query) {
        String upperQuery = query.toUpperCase();
        return Interval.getAllIntervals().stream().filter(a -> a.contains(upperQuery)).collect(Collectors.toList());
    }
}
