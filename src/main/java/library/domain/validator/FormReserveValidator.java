package library.domain.validator;

import library.domain.Timeslot;
import library.domain.User;
import library.domain.form.FormRoomReserve;
import library.domain.helper.RoomReservationHelper;
import library.domain.helper.UserHelper;
import library.services.room_reservation.RoomReservationService;
import library.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;
import java.util.List;

@Component
public class FormReserveValidator implements Validator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(FormReserveValidator.class);

	@Autowired
	private RoomReservationService roomReservationService;

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(FormRoomReserve.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		LOGGER.debug("Validating {}", target);
		FormRoomReserve form = (FormRoomReserve) target;
		validateHoursLimit(errors, form);
	}

	private void validateHoursLimit(Errors errors, FormRoomReserve form)
	{
		User u = UserHelper.getCurrentUser(userService);

		if (u == null)
			return;

		Date activeDate = RoomReservationHelper.getActiveDate(form.getDateIndex());
		List<Timeslot> timeslotList = form.getTimeslotList(userService);

		int maxReservation = roomReservationService.getReservationLimit(u.getRole());

		if (roomReservationService.getAllUserReservationByDate(activeDate, u.getId()).size() + timeslotList.size() >
				maxReservation)
		{
			errors.reject("reserve.limit", "You can only reserve up to " + maxReservation + " per day.");
		}
	}
}
