package library.repositories;

import library.domain.RoomReservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface RoomReservationRepository extends CrudRepository<RoomReservation, Integer>{
    @Transactional
    List<RoomReservation> findAllByDateReserved(Date date);
}
