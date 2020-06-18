package at.qe.sepm.skeleton.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService service;

    @GetMapping("/history")
    public List<HistoryEntry> getEntries() {
        return service.getHistoryItems();
    }

}