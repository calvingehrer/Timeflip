package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.HistoryItem;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends AbstractRepository<HistoryItem, Long> {

    @Query("SELECT h FROM HistoryItem h WHERE h.macAddress LIKE :macAddress")
    List<HistoryItem> getHistoryItemsPerTimeflip(@Param("macAddress") String macAddress);

}
