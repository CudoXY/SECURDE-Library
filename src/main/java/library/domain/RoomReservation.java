package library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class RoomReservation
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@ManyToOne
	private User reservedBy;

	@NotNull
	@ManyToOne
	private Room room;

	@NotNull
	private Date dateReserved;

	@NotNull
	private int timeReserved;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(User reservedBy) {
		this.reservedBy = reservedBy;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getDateReserved() {
		return dateReserved;
	}

	public void setDateReserved(Date dateReserved) {
		this.dateReserved = dateReserved;
	}

	public int getTimeReserved() {
		return timeReserved;
	}

	public void setTimeReserved(int timeReserved) {
		this.timeReserved = timeReserved;
	}
}
