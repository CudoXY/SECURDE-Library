package library.services.room;

import library.domain.Room;

import java.util.List;

public interface RoomService
{
	Room getRoomById(int id);
	List<Room> getAll();
}
