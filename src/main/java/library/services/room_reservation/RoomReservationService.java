package library.services.room_reservation;


import library.domain.RoomReservation;

import java.sql.Date;
import java.util.List;

public interface RoomReservationService
{
	RoomReservation reserveRoom(RoomReservation room, int userId);
	void cancelReservation(RoomReservation room, int userId);
	List<RoomReservation> getRoomReservationByDate(Date date);

	//    Staff
}
