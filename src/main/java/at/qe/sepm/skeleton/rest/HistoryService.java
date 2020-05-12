package at.qe.sepm.skeleton.rest;

import at.qe.sepm.skeleton.services.TimeflipService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HistoryService {

    @Autowired
    private TimeflipService timeflipService;

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    private static final ConcurrentLinkedQueue<HistoryEntry> HISTORY_QUEUE = new ConcurrentLinkedQueue<>();

    static Long getNextId() {
        return ID_COUNTER.getAndIncrement();
    }

    public HistoryEntry postHistoryObject(String macAddress, int facet, int seconds) {
        if (!StringUtils.hasText(macAddress)){
            throw new IllegalArgumentException("content must not be null or empty");
        }

        HistoryEntry newHistoryEntry = new HistoryEntry();
        newHistoryEntry.setMacAddress(macAddress);
        newHistoryEntry.setFacet(facet);
        newHistoryEntry.setSeconds(seconds);
        HISTORY_QUEUE.add(newHistoryEntry);
        return newHistoryEntry;
    }

    public List<HistoryEntry> getHistoryEntries() {
        return new ArrayList<>(HISTORY_QUEUE);
    }

    public HistoryEntry findHistoryEntries(Long id) {
        HistoryEntry retval = null;
        try {
            retval = HISTORY_QUEUE.stream().filter(msg -> msg.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException nsee) {
            // ignore for now ...
        }
        return retval;
    }

    public void deleteHistory(Long id) {
        HistoryEntry entry = findHistoryEntries(id);
        if (entry != null) {
            HISTORY_QUEUE.removeIf(msg -> msg.getId().equals(id));
        }
    }

    private String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

}