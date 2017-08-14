package library.services.room_reservation;


import library.domain.Room;
import library.domain.RoomReservation;

import java.sql.Date;
import java.util.List;

public interface RoomReservationService
{
	RoomReservation reserveRoom(RoomReservation room);
	void cancelReservation(RoomReservation room);
	List<RoomReservation> getRoomReservationByDate(Date date);
	RoomReservation getRoomReservationByRoomAndTimeReserved(int roomId, int timeReserved);

	//    Staff
}
