package library.repositories;

import library.domain.RoomReservation;
import org.springframework.data.repository.CrudRepository;

public interface RoomReservationRepository extends CrudRepository<RoomReservation, Integer>{
}
