package at.qe.sepm.skeleton.rest;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
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
public class MessageService {

    @Autowired
    private UserService userService;

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    private static final ConcurrentLinkedQueue<Message> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();

    static Long getNextId() {
        return ID_COUNTER.getAndIncrement();
    }

    public Message postMessage(String content) {
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("content must not be null or empty");
        }

        User user = userService.loadUser(getUserName());
        String userName = String.format("%s %s", user.getFirstName(), user.getLastName());

        Message newMessage = new Message(content, userName);
        MESSAGE_QUEUE.add(newMessage);
        return newMessage;
    }

    public Message appendMessage(String content, Long responseTo) {
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("content must not be null or empty");
        }

        User user = userService.loadUser(getUserName());
        String userName = String.format("%s %s", user.getFirstName(), user.getLastName());

        Message respondMessage = findMessage(responseTo);
        if (respondMessage == null) {
            throw new IllegalArgumentException("message is not known");
        }
        Message newMessage = new Message(content, userName, respondMessage.getId());
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