package at.qe.sepm.skeleton.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping("/messages")
    public List<Message> getMessages() {
        return service.getMessages();
    }

    @GetMapping("/messages/{id}")
    private Message getMessage(@PathVariable Long id) {
        return service.findMessage(id);
    }

    @PostMapping("/messages")
    private Message sendMessage(@RequestBody MessageRequest message) {
        return service.postMessage(message.getMacAddress(), message.getHistoryReadTime(), message.getHistory());
    }

    @DeleteMapping("/messages/{id}")
    private void deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
    }

}