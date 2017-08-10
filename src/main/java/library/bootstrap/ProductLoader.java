package library.bootstrap;

import library.domain.*;
import library.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent>
{

	private MaterialRepository materialRepository;
	private RoomRepository roomRepository;
	private RoomReservationRepository roomReservationRepository;
	private SecretQuestionRepository secretQuestionRepository;
	private UserRepository userRepository;

	private Logger log = Logger.getLogger(ProductLoader.class);

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}
	@Autowired
	public void setRoomReservationRepository(RoomReservationRepository roomReservationRepository) {
		this.roomReservationRepository = roomReservationRepository;
	}

	@Autowired
	public void setMaterialRepository(MaterialRepository materialRepository)
	{
		this.materialRepository = materialRepository;
	}

	@Autowired
	public void setSecretQuestionRepository(SecretQuestionRepository secretQuestionRepository)
	{
		this.secretQuestionRepository = secretQuestionRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
//		Room room = new Room();
//		room.setRoomName("Marnel Commons");
//		roomRepository.save(room);
//		room = new Room();
//		room.setRoomName("Pereddila Room");
//		roomRepository.save(room);
//		room = new Room();
//		room.setRoomName("Doctor Council Hall");
//		roomRepository.save(room);
//		room = new Room();
//		room.setRoomName("Room Despacity");
//		roomRepository.save(room);
//		room = new Room();
	//		room.setRoomName("Room Nonoczech");
//		roomRepository.save(room);

//		RoomReservation roomReservation = new RoomReservation();
//		roomReservation.setDateReserved(new Date(new java.util.Date().getTime()));
//		roomReservation.setReservedBy(userRepository.findOneById(123));
//		roomReservation.setRoom(roomRepository.findOneById(1));
//		roomReservation.setTimeReserved(7);
//		System.out.println(
//				roomReservation + " "  + roomReservation.getId() + " " + roomReservation.getDateReserved()  + " " + roomReservation.getReservedBy().getId() + " " +roomReservation.getRoom().getRoomName() + " " +roomReservation.getTimeReserved()
//		);
//		roomReservationRepository.save(roomReservation);

//		SecretQuestion s = new SecretQuestion();
//		s.setQuestion("What was the name of your elementary school?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("Where does your nearest sibling live?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("What is the name of your first pet?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("Where would like to live the most?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("What is your most favorite food?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("test question poh");
//		secretQuestionRepository.save(s);
//
//        Material m = new Material();
//        m.setId("book1");
//        m.setAuthor("Author 1");
//        m.setCategory(1);
//        m.setPublisher("Publisher 1");
//        m.setTitle("Book 1");
//        m.setYear(2017);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("book2");
//        m.setAuthor("Author 2");
//        m.setCategory(1);
//        m.setPublisher("Publisher 2");
//        m.setTitle("Book 2");
//        m.setYear(2010);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("book3");
//        m.setAuthor("Author 1");
//        m.setCategory(1);
//        m.setPublisher("Publisher 1");
//        m.setTitle("Book 3");
//        m.setYear(2017);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("book4");
//        m.setAuthor("Author 1");
//        m.setCategory(1);
//        m.setPublisher("Publisher 2");
//        m.setTitle("Book 4");
//        m.setYear(2012);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("book5");
//        m.setAuthor("Author 1");
//        m.setCategory(1);
//        m.setPublisher("Publisher 3");
//        m.setTitle("Book 5");
//        m.setYear(2015);
//        materialRepository.save(m);
//
//        // Magazine
//
//        m = new Material();
//        m.setId("magazine1");
//        m.setAuthor("Author 3");
//        m.setCategory(2);
//        m.setPublisher("Publisher 4");
//        m.setTitle("Magazine 3");
//        m.setYear(2017);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("magazine2");
//        m.setAuthor("Author 3");
//        m.setCategory(2);
//        m.setPublisher("Publisher 4");
//        m.setTitle("Magazine 3");
//        m.setYear(2015);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("magazine3");
//        m.setAuthor("Author 3");
//        m.setCategory(2);
//        m.setPublisher("Publisher 4");
//        m.setTitle("Magazine 4");
//        m.setYear(2017);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("magazine3");
//        m.setAuthor("Author 4");
//        m.setCategory(2);
//        m.setPublisher("Publisher 5");
//        m.setTitle("Magazine 5");
//        m.setYear(2016);
//        materialRepository.save(m);
//
//        // Thesis
//
//        m = new Material();
//        m.setId("thesis1");
//        m.setAuthor("Author 6");
//        m.setCategory(3);
//        m.setPublisher("Publisher 6");
//        m.setTitle("Thesis 1");
//        m.setYear(2017);
//        materialRepository.save(m);
//
//        m = new Material();
//        m.setId("thesis2");
//        m.setAuthor("Author 7");
//        m.setCategory(3);
//        m.setPublisher("Publisher 7");
//        m.setTitle("Thesis 2");
//        m.setYear(2017);
//        materialRepository.save(m);
	}
}
