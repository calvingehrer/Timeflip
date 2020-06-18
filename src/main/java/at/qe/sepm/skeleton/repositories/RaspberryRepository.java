package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Raspberry;

public interface RaspberryRepository extends AbstractRepository<Raspberry, String> {

    Raspberry findByRaspberryId(String raspberryId);
}