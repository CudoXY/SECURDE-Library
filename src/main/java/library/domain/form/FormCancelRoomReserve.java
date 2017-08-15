package library.domain.form;

import library.domain.Timeslot;
import library.domain.helper.UserHelper;
import library.services.user.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CudoXY on 8/14/2017.
 */
@Component
public class FormCancelRoomReserve
{
	private String timeslot;
	private int dateIndex;

	public String getTimeslot()
	{
		return timeslot;
	}

	public void setTimeslot(String timeslot)
	{
		this.timeslot = timeslot;
	}

	public Timeslot getTimeslot(UserService userService)
	{
		return parseTimeSlotString(timeslot, userService);
	}

	private Timeslot parseTimeSlotString(String timeSlot, UserService userService)
	{
		String[] valueSplit = timeSlot.split("_");

		Timeslot t = new Timeslot();
		t.setRoomId(Integer.parseInt(valueSplit[0]));
		t.setTime(Integer.parseInt(valueSplit[1]));
		t.setReservedBy(UserHelper.getCurrentUser(userService));

		return t;
	}

	public int getDateIndex()
	{
		return dateIndex;
	}

	public void setDateIndex(int dateIndex)
	{
		this.dateIndex = dateIndex;
	}
}
