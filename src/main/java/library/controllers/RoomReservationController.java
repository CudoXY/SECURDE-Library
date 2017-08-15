package library.controllers;

import library.domain.Room;
import library.domain.RoomReservation;
import library.domain.Timeslot;
import library.domain.form.FormCancelRoomReserve;
import library.domain.form.FormRegistration;
import library.domain.form.FormRoomReserve;
import library.domain.helper.RoomReservationHelper;
import library.domain.validator.FormReserveValidator;
import library.services.room.RoomService;
import library.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import library.services.room_reservation.RoomReservationService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Brandon on 7/31/2017.
 */
@Controller
@RequestMapping(value = "/roomreserve")
public class RoomReservationController
{

	private RoomReservationService roomReservationService;
	private RoomService roomService;
	private UserService userService;
	private FormReserveValidator formReserveValidator;

	@Autowired
	public void setFormReserveValidator(FormReserveValidator formReserveValidator)
	{
		this.formReserveValidator = formReserveValidator;
	}

	@Autowired
	public void setRoomService(RoomService roomService)
	{
		this.roomService = roomService;
	}

	@Autowired
	public void setRoomReservationService(RoomReservationService roomReservationService)
	{
		this.roomReservationService = roomReservationService;
	}

	@Autowired
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String load(@RequestParam(value = "d", required = false, defaultValue = "1") int dateIndex, Model model)
	{
		if (!model.containsAttribute("formRoomReserve")) {
			model.addAttribute("formRoomReserve", new FormRoomReserve());
		}

		final int TIMESLOTCOUNT = 15;
		final int STARTHOUR = 7;

		Date dateToView = RoomReservationHelper.getActiveDate(dateIndex);
		// Day to view

		List<Room> roomList = roomService.getAll();
		Timeslot[][] timeSlotList = new Timeslot[roomList.size()][TIMESLOTCOUNT];
		List<RoomReservation> roomReservationList = roomReservationService.getRoomReservationByDate(dateToView);

		int reservationIndex = 0;
		for (int i = 0; i < roomList.size(); i++)
		{
			for (int j = 0; j < TIMESLOTCOUNT; j++)
			{
				Timeslot t = new Timeslot();
				timeSlotList[i][j] = t;

				t.setRoomId(roomList.get(i).getId());
				t.setTime(STARTHOUR + j);

				// If no more reservation, just proceed initializing the other timeslots
				if (roomReservationList.size() <= reservationIndex)
				{
					continue;
				}

				if (roomReservationList.get(reservationIndex).getRoom().getId() != t.getRoomId() ||
						roomReservationList.get(reservationIndex).getTimeReserved() != t.getTime())
				{
					continue;
				}

				t.setReservedBy(roomReservationList.get(reservationIndex).getReservedBy());
				reservationIndex++;
			}
		}

		System.out.println(Arrays.deepToString(timeSlotList));

		model.addAttribute("formCancelRoomReserve", new FormCancelRoomReserve());

		Calendar c = Calendar.getInstance();
		c.setTime(new java.util.Date());
		List<java.util.Date> allowedDate = new ArrayList<>();

		for (int i = 0; i < RoomReservationHelper.ADVANCERANGE + 1; i++)
		{
			allowedDate.add(c.getTime());
			c.add(Calendar.DAY_OF_WEEK, 1);
		}


		model.addAttribute("activeDateIndex", dateIndex);
		model.addAttribute("allowedDateList", allowedDate);
		model.addAttribute("TIMESLOTCOUNT", TIMESLOTCOUNT);
		model.addAttribute("roomList", roomList);
		model.addAttribute("timeSlotList", timeSlotList);

		return "user/roomreserve";
	}


	@InitBinder("formRoomReserve")
	public void initBinder(WebDataBinder binder)
	{
		binder.addValidators(formReserveValidator);
	}

	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(@Valid @ModelAttribute("formRoomReserve") final FormRoomReserve formRoomReserve, final BindingResult bindingResult,
						  final RedirectAttributes redirectAttributes)
	{
		System.out.println(String.format("Processing user create form=%s, bindingResult=%s", formRoomReserve, bindingResult));

		if (bindingResult.hasErrors())
		{
			// failed validation
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.formRoomReserve", bindingResult);
			redirectAttributes.addFlashAttribute("formRoomReserve", formRoomReserve);
			return "redirect:/roomreserve?d=" + formRoomReserve.getDateIndex();
		}

		List<Timeslot> timeslotList = formRoomReserve.getTimeslotList(userService);
		Date d = RoomReservationHelper.getActiveDate(formRoomReserve.getDateIndex());

		System.out.println("d = " + d);

		for (int i = 0; i < timeslotList.size(); i++)
		{
			Timeslot t = timeslotList.get(i);

			// Check first if taken
			RoomReservation currReservation = roomReservationService.getRoomReservationByDateRoomTimeReserved(
					d, t.getRoomId(), t.getTime());

			if (currReservation != null)
			{
				// TODO: Error
				continue;
			}

			RoomReservation r = new RoomReservation();
			r.setDateReserved(d);
			r.setTimeReserved(t.getTime());
			r.setRoom(roomService.getRoomById(t.getRoomId()));
			r.setReservedBy(t.getReservedBy());

			roomReservationService.reserveRoom(r);
		}

		return "redirect:/roomreserve?d=" + formRoomReserve.getDateIndex();
	}

	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(FormCancelRoomReserve formCancelRoomReserve, Model model)
	{
		Timeslot t = formCancelRoomReserve.getTimeslot(userService);
		Date d = RoomReservationHelper.getActiveDate(formCancelRoomReserve.getDateIndex());
		String redirectPath = "redirect:/roomreserve?d=" + formCancelRoomReserve.getDateIndex();

		// Check first if taken
		RoomReservation currReservation = roomReservationService.getRoomReservationByDateRoomTimeReserved(
				d, t.getRoomId(), t.getTime());

		if (currReservation == null)
		{
			// TODO: Error
			return redirectPath;
		}

		if (currReservation.getReservedBy().getId() != t.getReservedBy().getId())
		{
			// TODO: Error
			return redirectPath;
		}

		roomReservationService.cancelReservation(currReservation);

		return redirectPath;
	}
}
