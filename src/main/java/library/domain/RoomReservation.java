package library.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class RoomReservation
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn
	private User reservedBy;

	private int roomId;
	private Date dateReserved;
	private int timeReserved;


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public User getReservedBy()
	{
		return reservedBy;
	}

	public void setReservedBy(User reservedBy)
	{
		this.reservedBy = reservedBy;
	}

	public int getRoomId()
	{
		return roomId;
	}

	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}

	public Date getDateReserved()
	{
		return dateReserved;
	}

	public void setDateReserved(Date dateReserved)
	{
		this.dateReserved = dateReserved;
	}

	public int getTimeReserved()
	{
		return timeReserved;
	}

	public void setTimeReserved(int timeReserved)
	{
		this.timeReserved = timeReserved;
	}
}
