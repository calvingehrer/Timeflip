package at.qe.sepm.skeleton.rest;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.HistoryRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HistoryService {

    @Autowired
    public HistoryRepository historyRepository;

    @Autowired
    public TimeflipRepository timeflipRepository;

    @Autowired
    public TaskRepository taskRepository;

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    private static final ConcurrentLinkedQueue<HistoryEntry> HISTORY_QUEUE = new ConcurrentLinkedQueue<>();

    /**
     * @return autoincremented ID
     */
    static Long getNextId() {
        return ID_COUNTER.getAndIncrement();
    }


    /**
     * Creates a HistoryEntry object and adds it to the HISTORY_QUEUE.
     *
     * @param macAddress the mac address of the TimeFlip device
     * @param facet the facet of the HistoryEntry object
     * @param start the start date of the HistoryEntry object
     * @param end the end date of the HistoryEntry object
     * @param seconds the deuration in seconds of the HistoryEntry object
     * @return the newly created HistoryEntry object
     */
    public HistoryEntry postHistoryObject(String macAddress, int facet, Date start, Date end, int seconds, int battery) {
        if (!StringUtils.hasText(macAddress)){
            throw new IllegalArgumentException("content must not be null or empty");
        }

        HistoryEntry historyEntry = new HistoryEntry();
        historyEntry.setId(getNextId());
        historyEntry.setMacAddress(macAddress);
        historyEntry.setFacet(facet);
        historyEntry.setStart(start);
        historyEntry.setEnd(end);
        historyEntry.setSeconds(seconds);
        historyEntry.setBattery(battery);
        addAsTask(historyEntry);
        HISTORY_QUEUE.add(historyEntry);

        return historyEntry;
    }


    /**
     * @return the HistoryEntry objects in HISTORY_QUEUE as a list.
     */
    public List<HistoryEntry> getHistoryItems() {
        return new ArrayList<>(HISTORY_QUEUE);
    }


    /**
     * Finds HistoryEntry object based on the id.
     *
     * @param id the id of the HistoryEntry object
     * @return the requested HistoryEntry object, null if not found
     */
    public HistoryEntry findHistoryEntry(Long id) {
        HistoryEntry retval = null;
        try {
            retval = HISTORY_QUEUE.stream().filter(msg -> msg.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException nsee) {
            // ignore for now ...
        }
        return retval;
    }


    /**
     * Creates Task object based on the given HistoryEntry and adds it to the database
     *
     * @param historyEntry
     * @return the created Task object
     */
    public Task addAsTask(HistoryEntry historyEntry){
        Task task = new Task();
        Timeflip timeflip = timeflipRepository.findByMacAddress(historyEntry.getMacAddress());
        if(timeflip != null){
            User user = timeflip.getUser();
            timeflip.setBattery(historyEntry.getBattery());
            if(user != null){
                task.setTeam(user.getTeam());
                task.setDepartment(user.getDepartment());
                task.setUser(user);
            }
            int facet = historyEntry.getFacet();

            switch (facet){
                case 1: task.setTask(timeflip.getFacet1()); break;
                case 2: task.setTask(timeflip.getFacet2()); break;
                case 3: task.setTask(timeflip.getFacet3()); break;
                case 4: task.setTask(timeflip.getFacet4()); break;
                case 5: task.setTask(timeflip.getFacet5()); break;
                case 6: task.setTask(timeflip.getFacet6()); break;
                case 7: task.setTask(timeflip.getFacet7()); break;
                case 8: task.setTask(timeflip.getFacet8()); break;
                case 9: task.setTask(timeflip.getFacet9()); break;
                case 10: task.setTask(timeflip.getFacet10()); break;
                case 11: task.setTask(timeflip.getFacet11()); break;
                case 12: task.setTask(timeflip.getFacet12()); break;
                default: task.setTask(TaskEnum.NOT_DEFINED); break;
            }
        }
        task.setStartTime(historyEntry.getStart().toInstant());
        task.setEndTime(historyEntry.getEnd().toInstant());
        task.setSeconds(historyEntry.getSeconds());
        task.setCreateDate(new Date());

        taskRepository.save(task);
        return task;
    }


    /**
     * Deletes the HistoryEntry with the given id
     *
     * @param id the id if the HistoryEntry
     */
    public void deleteHistoryEntry(Long id) {
        HistoryEntry historyEntry = findHistoryEntry(id);
        if (historyEntry != null) {
            HISTORY_QUEUE.removeIf(msg -> msg.getId().equals(id));
        }
    }
}