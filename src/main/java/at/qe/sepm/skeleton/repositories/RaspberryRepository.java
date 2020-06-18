package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Timeflip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RaspberryRepository extends AbstractRepository<Raspberry, String> {

    Raspberry findByRaspberryId(String raspberryId);
}