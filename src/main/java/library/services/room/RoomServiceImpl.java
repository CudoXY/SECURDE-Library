package library.services.room;

import library.domain.Room;
import library.domain.SecretQuestion;
import library.repositories.RoomRepository;
import library.repositories.SecretQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService
{
    private RoomRepository roomRepository;

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository)
    {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room getRoomById(int id)
    {
        return roomRepository.findOneById(id);
    }

    @Override
    public List<Room> getAll()
    {
        return roomRepository.findAll();
    }
}
