package at.qe.sepm.skeleton.repositories;

import at.qe.sepm.skeleton.model.Room;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends AbstractRepository<Room, String> {

    Room findByRoomNumber(String roomNumber);

    @Query("SELECT r FROM Room r")
    List<Room> findRoomsWithoutRaspberry();
}