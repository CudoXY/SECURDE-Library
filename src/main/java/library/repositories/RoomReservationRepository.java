package library.repositories;

import library.domain.Room;
import library.domain.RoomReservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface RoomReservationRepository extends CrudRepository<RoomReservation, Integer>{

    @Transactional
    List<RoomReservation> findAllByDateReservedOrderByRoom_IdAscTimeReservedAsc(Date date);

    @Transactional
    RoomReservation findOneByDateReservedAndRoom_IdAndTimeReserved(Date dateReserved, int roomId, int timeReserved);

    List<RoomReservation> findAllByDateReservedAndReservedBy_Id(Date activeDate, int reservedById);
}
