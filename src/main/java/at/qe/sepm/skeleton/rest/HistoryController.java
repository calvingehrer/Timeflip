package at.qe.sepm.skeleton.rest;

import java.util.List;

import at.qe.sepm.skeleton.model.HistoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService service;

    @GetMapping("/history")
    public List<HistoryItem> getEntries() {
        return service.getHistoryItems();
    }

    @PostMapping("/history")
    private HistoryItem sendMessage(@RequestBody HistoryEntry entry) {
        return service.postHistoryObject(entry.getMacAddress(), entry.getFacet(), entry.getStart(), entry.getEnd(), entry.getSeconds());
    }

    @DeleteMapping("/history/{id}")
    private void deleteHistory(@PathVariable Long id) {
        service.deleteHistory(id);
    }

}