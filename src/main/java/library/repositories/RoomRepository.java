package library.repositories;

import library.domain.Review;
import library.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoomRepository extends CrudRepository<Room, Integer>{
    Room findOneById(int id);
}
