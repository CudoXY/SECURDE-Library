package library.controllers;

import library.domain.Room;
import library.domain.RoomReservation;
import library.domain.Timeslot;
import library.services.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import library.services.RoomReservationService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 7/31/2017.
 */
@Controller
public class RoomReservationController {
    private RoomReservationService roomReservationService;
    private RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    public void setRoomReservationService(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @RequestMapping(value = "/roomreserve", method = RequestMethod.GET)
    public String roomreserve(Model model) {
        final int TIMESLOTCOUNT = 15;
        ArrayList<Timeslot> timeSlotList = new ArrayList<Timeslot>();
        List<RoomReservation> roomReservations = roomReservationService.getRoomReservationByDate(new Date(new java.util.Date().getTime()));
        List<Room> roomList = roomService.getAll();

        model.addAttribute("roomList", roomList);
        int reserveIndex = 0;
        for (int i = 0; i < roomList.size(); i++) {
            for (int x = 0; x < TIMESLOTCOUNT; x++) {
                Timeslot ts = new Timeslot();
                timeSlotList.add(ts);

                ts.setRoomId(roomList.get(i).getId());
                ts.setTime(x);

                if (roomReservations.size() <= reserveIndex)
                    continue;

                if (roomReservations.get(reserveIndex).getRoom().getId() != roomList.get(i).getId())
                    continue;

                ts.setReservedBy(roomReservations.get(reserveIndex).getReservedBy());
                ts.setTime(roomReservations.get(reserveIndex).getTimeReserved());
                reserveIndex++;
            }

        }
        System.out.println("timeSlotList = " + timeSlotList.size());
        model.addAttribute("timeSlotList",
                timeSlotList);
        model.addAttribute("timeSlotCount",
                TIMESLOTCOUNT);
        return "user/roomreserve";
    }
}
