package library.services;

import library.domain.RoomReservation;
import library.domain.User;
import library.repositories.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    private RoomReservationRepository roomReservationRepository;

    @Autowired
    public void setRoomReservationRepository(RoomReservationRepository roomReservationRepository) {
        this.roomReservationRepository = roomReservationRepository;
    }

    @Override
    public RoomReservation reserveRoom(RoomReservation room, int userId)
    {
        User u = new User();
        u.setId(userId);

        room.setReservedBy(u);
        room.setDateReserved(new java.sql.Date(new java.util.Date().getTime()));
        return roomReservationRepository.save(room);
    }

    @Override
    public void cancelReservation(RoomReservation room, int userId)
    {
        roomReservationRepository.delete(room);
    }

    @Override
    public List<RoomReservation> getRoomReservationByDate(Date date)
    {
       return roomReservationRepository.findAllByDateReserved(date);
    }
}
