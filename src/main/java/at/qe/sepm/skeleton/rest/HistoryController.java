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

    @PostMapping("/history")
    private HistoryEntry sendMessage(@RequestBody HistoryEntry entry) {
        return service.postHistoryObject(entry.getMacAddress(), entry.getFacet(), entry.getStart(), entry.getEnd(), entry.getSeconds(), entry.getBattery());
    }

    @DeleteMapping("/history/{id}")
    private void deleteHistory(@PathVariable Long id) {
        service.deleteHistoryEntry(id);
    }

}