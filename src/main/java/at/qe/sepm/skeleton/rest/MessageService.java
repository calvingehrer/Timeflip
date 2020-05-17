package at.qe.sepm.skeleton.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageService {

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    private static final ConcurrentLinkedQueue<Message> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();

    static Long getNextId() {
        return ID_COUNTER.getAndIncrement();
    }

    public Message postMessage(String macAddress, String historyReadTime, String history) {
        if (!StringUtils.hasText(macAddress) || !StringUtils.hasText(historyReadTime) || !StringUtils.hasText(history)) {
            throw new IllegalArgumentException("content must not be null or empty");
        }

        Message newMessage = new Message(macAddress, historyReadTime, history);
        MESSAGE_QUEUE.add(newMessage);
        return newMessage;
    }

    public List<Message> getMessages() {
        return new ArrayList<>(MESSAGE_QUEUE);
    }

    public Message findMessage(Long id) {
        Message retval = null;
        try {
            retval = MESSAGE_QUEUE.stream().filter(msg -> msg.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException nsee) {
            // ignore for now ...
        }
        return retval;
    }

    public void deleteMessage(Long id) {
        Message message = findMessage(id);
        if (message != null) {
            MESSAGE_QUEUE.removeIf(msg -> msg.getId().equals(id));
        }
    }

    private String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

}