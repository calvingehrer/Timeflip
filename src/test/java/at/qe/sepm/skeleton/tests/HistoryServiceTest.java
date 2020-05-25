package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.rest.HistoryEntry;
import at.qe.sepm.skeleton.rest.HistoryService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class HistoryServiceTest {

    @Autowired
    HistoryService historyService;
    @Autowired
    TaskRepository taskRepository;

    @Test
    void testPostHistoryObject(){
        String macAddress = "0C:61:CF:C7:CE:20";
        int facet = 2;
        long now = new Date().getTime();
        Date start = new Date(now - TimeUnit.HOURS.toMillis(1));
        Date end = new Date();
        int seconds = (int) (end.getTime()-start.getTime());

        HistoryEntry newHistoryEntry = historyService.postHistoryObject(macAddress, facet, start, end, seconds);
        Assert.assertNotNull("History object could not be created", newHistoryEntry);
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected macAddress", macAddress, newHistoryEntry.getMacAddress());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected facet number", facet, newHistoryEntry.getFacet());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected start time", start, newHistoryEntry.getStart());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected end time", end, newHistoryEntry.getEnd());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected duration in seconds", seconds, newHistoryEntry.getSeconds());
    }

    @Test
    void testFindHistoryEntries(){
        String macAddress = "0C:61:CF:C7:CE:20";
        int facet = 2;
        long now = new Date().getTime();
        Date start = new Date(now - TimeUnit.HOURS.toMillis(1));
        Date end = new Date();
        int seconds = (int) (end.getTime()-start.getTime());

        HistoryEntry expectedHistoryEntry = historyService.postHistoryObject(macAddress, facet, start, end, seconds);
        HistoryEntry requestedHistoryEntry = historyService.findHistoryEntry((long) 2);

        Assert.assertNotNull("History Entry with given id could not be found", requestedHistoryEntry);
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected macAddress", expectedHistoryEntry.getMacAddress(), requestedHistoryEntry.getMacAddress());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected facet number", expectedHistoryEntry.getFacet(), requestedHistoryEntry.getFacet());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected start time", expectedHistoryEntry.getStart(), requestedHistoryEntry.getStart());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected end time", expectedHistoryEntry.getEnd(), requestedHistoryEntry.getEnd());
        Assert.assertEquals("HistoryEntry \"newHistoryEntry\" does not contain the expected duration in seconds", expectedHistoryEntry.getSeconds(), requestedHistoryEntry.getSeconds());
    }

    @Test
    void testGetHistoryItems(){
        Assert.assertNotNull(historyService.getHistoryItems());
        Assert.assertEquals("Size of HistoryEntry queue not as expected", 0, historyService.getHistoryItems().size());
    }


    @Test
    void testDeleteHistoryEntry(){
        HistoryEntry requestedHistoryEntry = historyService.findHistoryEntry((long) 2);
        historyService.deleteHistoryEntry((long) 2);

        Assert.assertFalse("HistoryEntry \"requestedHistoryEntry\" still in HistoryEntry queue", historyService.getHistoryItems().contains(requestedHistoryEntry));
    }

}