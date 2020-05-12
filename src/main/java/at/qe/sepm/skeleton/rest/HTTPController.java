package at.qe.sepm.skeleton.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HTTPController {

    @Autowired
    private HistoryService service;

    @GetMapping("/history")
    public List<HistoryEntry> getEntries() {
        return service.getHistoryEntries();
    }

    @PostMapping("/history")
    private HistoryEntry sendMessage(@RequestBody HistoryRequest history) {
        return service.postHistoryObject(history.getMacAddress(), history.getFacet(), history.getSeconds());
    }

    @DeleteMapping("/history/{id}")
    private void deleteHistory(@PathVariable Long id) {
        service.deleteHistory(id);
    }

}