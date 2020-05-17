package at.qe.sepm.skeleton.rest;

import at.qe.sepm.skeleton.model.HistoryItem;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.HistoryRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.TimeflipRepository;
import at.qe.sepm.skeleton.utils.MessagesView;
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

    static Long getNextId() {
        return ID_COUNTER.getAndIncrement();
    }

    public HistoryEntry postHistoryObject(String macAddress, int facet, Date start, Date end, int seconds) {
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
        addAsTask(historyEntry);
        HISTORY_QUEUE.add(historyEntry);

        return historyEntry;
    }

    public List<HistoryEntry> getHistoryItems() {
        return new ArrayList<>(HISTORY_QUEUE);
    }

    public HistoryEntry findHistoryItems(Long id) {
        HistoryEntry retval = null;
        try {
            retval = HISTORY_QUEUE.stream().filter(msg -> msg.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException nsee) {
            // ignore for now ...
        }
        return retval;
    }

    public void addAsTask(HistoryEntry historyEntry){
        Task task = new Task();
        //Timeflip timeflip = timeflipRepository.findByMacAddress(historyEntry.getMacAddress());
        /*if(timeflip != null){
            User user = timeflip.getUser();
            task.setTeam(user.getTeam());
            task.setDepartment(user.getDepartment());
            task.setUser(user);
        }*/
        task.setStartTime(historyEntry.getStart().toInstant());
        task.setEndTime(historyEntry.getEnd().toInstant());
        task.setSeconds(historyEntry.getSeconds());
        task.setCreateDate(new Date());

        taskRepository.save(task);
    }

    public void deleteHistory(Long id) {
        HistoryEntry historyEntry = findHistoryItems(id);
        if (historyEntry != null) {
            HISTORY_QUEUE.removeIf(msg -> msg.getId().equals(id));
        }
    }

    private String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

}