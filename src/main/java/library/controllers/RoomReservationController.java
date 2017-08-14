package library.controllers;

import library.domain.Room;
import library.domain.RoomReservation;
import library.domain.Timeslot;
import library.domain.form.FormCancelRoomReserve;
import library.domain.form.FormRoomReserve;
import library.services.room.RoomService;
import library.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import library.services.room_reservation.RoomReservationService;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
	public String roomreserve(Model model)
	{
		final int TIMESLOTCOUNT = 15;
		final int STARTHOUR = 7;

		List<Room> roomList = roomService.getAll();
		Timeslot[][] timeSlotList = new Timeslot[roomList.size()][TIMESLOTCOUNT];
		List<RoomReservation> roomReservationList = roomReservationService.getRoomReservationByDate(new Date(new java.util.Date().getTime()));

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

		model.addAttribute("formRoomReserve", new FormRoomReserve());
		model.addAttribute("formCancelRoomReserve", new FormCancelRoomReserve());
		model.addAttribute("dateToday", new Date(new java.util.Date().getTime()));
		model.addAttribute("TIMESLOTCOUNT", TIMESLOTCOUNT);
		model.addAttribute("roomList", roomList);
		model.addAttribute("timeSlotList", timeSlotList);

		return "user/roomreserve";
	}

	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(FormRoomReserve formRoomReserve, Model model)
	{
		List<Timeslot> timeslotList = formRoomReserve.getTimeslotList(userService);

		for (int i = 0; i < timeslotList.size(); i++)
		{
			Timeslot t = timeslotList.get(i);

			// Check first if taken
			RoomReservation currReservation = roomReservationService.getRoomReservationByRoomAndTimeReserved(
					t.getRoomId(), t.getTime());

			if (currReservation != null)
			{
				// TODO: Error
				continue;
			}

			RoomReservation r = new RoomReservation();
			r.setTimeReserved(t.getTime());
			r.setRoom(roomService.getRoomById(t.getRoomId()));
			r.setReservedBy(t.getReservedBy());

			roomReservationService.reserveRoom(r);
		}

		return "redirect:/roomreserve";
	}

	@PreAuthorize("hasAnyRole('STUDENT', 'FACULTY')")
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(FormCancelRoomReserve formCancelRoomReserve, Model model)
	{
		Timeslot t = formCancelRoomReserve.getTimeslot(userService);

		// Check first if taken
		RoomReservation currReservation = roomReservationService.getRoomReservationByRoomAndTimeReserved(
				t.getRoomId(), t.getTime());

		if (currReservation == null)
		{
			// TODO: Error
			return "redirect:/roomreserve";
		}

		if (currReservation.getReservedBy().getId() != t.getReservedBy().getId())
		{
			// TODO: Error
			return "redirect:/roomreserve";
		}

		roomReservationService.cancelReservation(currReservation);

		return "redirect:/roomreserve";
	}
}
