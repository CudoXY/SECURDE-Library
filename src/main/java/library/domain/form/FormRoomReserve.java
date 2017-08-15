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
public class FormRoomReserve
{
	private int dateIndex;
	private String timeslot;

	public String getTimeslot()
	{
		return timeslot;
	}

	public void setTimeslot(String timeslot)
	{
		this.timeslot = timeslot;
	}

	public List<Timeslot> getTimeslotList(UserService userService)
	{
		System.out.println("timeslot = " + timeslot);
		return parseTimeSlotString(timeslot, userService);
	}

	private List<Timeslot> parseTimeSlotString(String timeSlot, UserService userService)
	{
		List<Timeslot> timeSlotList = new ArrayList<Timeslot>();

		String[] timeSlotSplit = timeSlot.split("\\|");

		for (int i = 0; i < timeSlotSplit.length; i++)
		{
			System.out.println("timeSlotSplit[i] = " + timeSlotSplit[i]);
			String[] valueSplit = timeSlotSplit[i].split("_");

			Timeslot t = new Timeslot();
			t.setRoomId(Integer.parseInt(valueSplit[0]));
			t.setTime(Integer.parseInt(valueSplit[1]));
			t.setReservedBy(UserHelper.getCurrentUser(userService));

			timeSlotList.add(t);
		}

		return timeSlotList;
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
