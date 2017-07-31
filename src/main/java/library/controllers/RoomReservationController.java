package library.controllers;

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
    public void setRoomReservationService(RoomReservationService roomReservationService) { this.roomReservationService = roomReservationService;}

    @RequestMapping(value = "/roomreserve", method = RequestMethod.GET)
    public String roomreserve(Model model){
        model.addAttribute("reservationList",
                roomReservationService.getRoomReservationByDate(new java.sql.Date(new java.util.Date().getTime())));
        model.addAttribute("roomList",
                roomService.getAll());
        return "user/roomreserve";
    }
}
