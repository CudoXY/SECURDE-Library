package library.services.room_reservation;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import library.domain.Role;
import library.domain.RoomReservation;
import library.repositories.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    private RoomReservationRepository roomReservationRepository;

    private static final int QUOTA_STUDENT = 3;
    private static final int QUOTA_FACULTY = 5;

    @Autowired
    public void setRoomReservationRepository(RoomReservationRepository roomReservationRepository) {
        this.roomReservationRepository = roomReservationRepository;
    }

    @Override
    public RoomReservation reserveRoom(RoomReservation room)
    {
        return roomReservationRepository.save(room);
    }

    @Override
    public void cancelReservation(RoomReservation room)
    {
        roomReservationRepository.delete(room);
    }

    @Override
    public List<RoomReservation> getRoomReservationByDate(Date date)
    {
       return roomReservationRepository.findAllByDateReservedOrderByRoom_IdAscTimeReservedAsc(date);
    }

    @Override
    public RoomReservation getRoomReservationByDateRoomTimeReserved(Date date, int roomId, int timeReserved)
    {
        return roomReservationRepository.findOneByDateReservedAndRoom_IdAndTimeReserved(date, roomId, timeReserved);
    }

    @Override
    public List<RoomReservation> getAllUserReservationByDate(Date activeDate, int userId)
    {
        return roomReservationRepository.findAllByDateReservedAndReservedBy_Id(activeDate, userId);
    }

    @Override
    public int getReservationLimit(Role role)
    {
        switch (role)
        {
            case ROLE_STUDENT:
                return QUOTA_STUDENT;
            case ROLE_FACULTY:
                return QUOTA_FACULTY;
            default:
                return Integer.MAX_VALUE;
        }
    }
}
