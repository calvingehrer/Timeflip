package at.qe.sepm.skeleton.rest;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.HistoryRepository;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

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
     * @param facet      the facet of the HistoryEntry object
     * @param start      the start date of the HistoryEntry object
     * @param end        the end date of the HistoryEntry object
     * @param seconds    the deuration in seconds of the HistoryEntry object
     * @return the newly created HistoryEntry object
     */
    public HistoryEntry postHistoryObject(String macAddress, int facet, Date start, Date end, int seconds) {
        if (!StringUtils.hasText(macAddress)) {
            throw new IllegalArgumentException("content must not be null or empty");
        }

        HistoryEntry historyEntry = new HistoryEntry();
        historyEntry.setId(getNextId());
        historyEntry.setMacAddress(macAddress);
        historyEntry.setFacet(facet);
        historyEntry.setStart(start);
        historyEntry.setEnd(end);
        historyEntry.setSeconds(seconds);
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
    public Task addAsTask(HistoryEntry historyEntry) {
        Task task = new Task();
        Timeflip timeflip = timeflipRepository.findByMacAddress(historyEntry.getMacAddress());
        if (timeflip != null) {
            User user = timeflip.getUser();
            if (user != null) {
                task.setTeam(user.getTeam());
                task.setDepartment(user.getDepartment());
                task.setUser(user);
            }
            int facet = historyEntry.getFacet();
            task.setTask(TaskEnum.values()[facet - 1]);
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