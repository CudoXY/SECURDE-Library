package library.domain.helper;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by CudoXY on 8/15/2017.
 */
public class RoomReservationHelper
{
	public static final int ADVANCERANGE = 1;

	public static Date getActiveDate(int dateIndex)
	{
		// Clamp dateIndex parameter
		dateIndex = clamp(dateIndex, 1, ADVANCERANGE + 1);

		Calendar c = Calendar.getInstance();
		c.setTime(new java.util.Date());

		for (int i = 0; i < dateIndex - 1; i++)
			c.add(Calendar.DAY_OF_WEEK, 1);

		return new Date(c.getTimeInMillis());
	}

	private static int clamp(int val, int min, int max) {
		return Math.max(min, Math.min(max, val));
	}
}
