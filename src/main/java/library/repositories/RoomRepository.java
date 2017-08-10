package library.repositories;

import library.domain.Review;
import library.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer>{

    @Transactional
    Room findOneById(int id);

    @Transactional
    List<Room> findAll();
}
