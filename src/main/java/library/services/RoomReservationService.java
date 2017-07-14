package library.services;


import library.domain.RoomReservation;

public interface RoomReservationService
{
	RoomReservation reserveRoom(RoomReservation room, int userId);
	void cancelReservation(RoomReservation room, int userId);
	void getRoomReservationByDateTime(RoomReservation roomReservation);

	//    Staff

}
