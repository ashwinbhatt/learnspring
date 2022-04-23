package com.ashwinbhatt.learnspring.webservices;

import com.ashwinbhatt.learnspring.business.GuestModel;
import com.ashwinbhatt.learnspring.business.ReservationService;
import com.ashwinbhatt.learnspring.business.RoomReservation;
import com.ashwinbhatt.learnspring.data.Guest;
import com.ashwinbhatt.learnspring.data.Room;
import com.ashwinbhatt.learnspring.util.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebserviceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    public WebserviceController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @RequestMapping(path="/reservations", method = RequestMethod.GET)
    public List<RoomReservation> getReservations(@RequestParam(value="date", required = false) String dateString){
        Date date = this.dateUtils.createDateFromDateString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }

    @RequestMapping(path="/guests", method = RequestMethod.GET)
    public List<GuestModel> getGuests(){
        return this.reservationService.getGuestDetails();
    }

    @RequestMapping(path = "/guests", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Guest setGuest(@RequestBody Guest guest){
        return this.reservationService.addGuest(guest);
    }

    @RequestMapping(path = "/rooms", method = RequestMethod.GET)
    public List<Room> getRooms(){
        return this.reservationService.getRooms();
    }

}
