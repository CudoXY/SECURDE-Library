package library.services.room_reservation;


import library.domain.Role;
import library.domain.Room;
import library.domain.RoomReservation;

import java.sql.Date;
import java.util.Dictionary;
import java.util.List;

public interface RoomReservationService
{
	RoomReservation reserveRoom(RoomReservation room);
	void cancelReservation(RoomReservation room);
	List<RoomReservation> getRoomReservationByDate(Date date);
	RoomReservation getRoomReservationByDateRoomTimeReserved(Date date, int roomId, int timeReserved);

	List<RoomReservation> getAllUserReservationByDate(Date activeDate, int userId);
	int getReservationLimit(Role role);

	//    Staff
}
