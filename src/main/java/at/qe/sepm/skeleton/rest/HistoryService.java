package at.qe.sepm.skeleton.rest;

import at.qe.sepm.skeleton.model.HistoryItem;
import at.qe.sepm.skeleton.repositories.HistoryRepository;

import java.util.ArrayList;
import java.util.Date;
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
    public HistoryRepository historyRepository;

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    private static final ConcurrentLinkedQueue<HistoryItem> HISTORY_QUEUE = new ConcurrentLinkedQueue<>();

    static Long getNextId() {
        return ID_COUNTER.getAndIncrement();
    }

    public HistoryItem postHistoryObject(String macAddress, int facet, Date start, Date end, int seconds) {
        if (!StringUtils.hasText(macAddress)){
            throw new IllegalArgumentException("content must not be null or empty");
        }

        //HistoryEntry newHistoryEntry = new HistoryEntry();
        //newHistoryEntry.setMacAddress(macAddress);
        //newHistoryEntry.setFacet(facet);
        //newHistoryEntry.setSeconds(seconds);

        HistoryItem historyItem = new HistoryItem();
        historyItem.setId(getNextId());
        historyItem.setMacAddress(macAddress);
        historyItem.setFacet(facet);
        historyItem.setStart(start);
        historyItem.setEnd(end);
        historyItem.setSeconds(seconds);
        addHistoryItem(historyItem);
        HISTORY_QUEUE.add(historyItem);

        return historyItem;
    }

    public List<HistoryItem> getHistoryItems() {
        return new ArrayList<>(HISTORY_QUEUE);
    }

    public HistoryItem findHistoryItems(Long id) {
        HistoryItem retval = null;
        try {
            retval = HISTORY_QUEUE.stream().filter(msg -> msg.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException nsee) {
            // ignore for now ...
        }
        return retval;
    }

    public HistoryItem addHistoryItem(HistoryItem historyItem){
        HistoryItem newHistoryItem = new HistoryItem();
        newHistoryItem.setId(getNextId());
        newHistoryItem.setMacAddress(historyItem.getMacAddress());
        newHistoryItem.setFacet(historyItem.getFacet());
        newHistoryItem.setSeconds(historyItem.getSeconds());

        saveHistoryItem(newHistoryItem);

        return newHistoryItem;
    }

    public HistoryItem saveHistoryItem(HistoryItem historyItem){
        return historyRepository.save(historyItem);
    }

    public void deleteHistory(Long id) {
        HistoryItem item = findHistoryItems(id);
        if (item != null) {
            HISTORY_QUEUE.removeIf(msg -> msg.getId().equals(id));
        }
    }

    private String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

}