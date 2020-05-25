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

    public HistoryEntry findHistoryEntry(Long id) {
        HistoryEntry retval = null;
        try {
            retval = HISTORY_QUEUE.stream().filter(msg -> msg.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException nsee) {
            // ignore for now ...
        }
        return retval;
    }

    public Task addAsTask(HistoryEntry historyEntry){
        Task task = new Task();
        Timeflip timeflip = timeflipRepository.findByMacAddress(historyEntry.getMacAddress());
        if(timeflip != null){
            User user = timeflip.getUser();
            task.setTeam(user.getTeam());
            task.setDepartment(user.getDepartment());
            task.setUser(user);
            int facet = historyEntry.getFacet();
            task.setTask(TaskEnum.values()[facet-1]);
        }
        task.setStartTime(historyEntry.getStart().toInstant());
        task.setEndTime(historyEntry.getEnd().toInstant());
        task.setSeconds(historyEntry.getSeconds());
        task.setCreateDate(new Date());

        taskRepository.save(task);
        return task;
    }

    public void deleteHistoryEntry(Long id) {
        HistoryEntry historyEntry = findHistoryEntry(id);
        if (historyEntry != null) {
            HISTORY_QUEUE.removeIf(msg -> msg.getId().equals(id));
        }
    }
}