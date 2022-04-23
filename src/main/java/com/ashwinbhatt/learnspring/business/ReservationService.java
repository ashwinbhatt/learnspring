package com.ashwinbhatt.learnspring.business;

import com.ashwinbhatt.learnspring.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    private final RoomRepository roomRepository;

    private final GuestRepository guestRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.guestRepository = null;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(Long.valueOf(reservation.getRoomId()));
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if (o1.getRoomName().equals(o2.getRoomName())) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        return roomReservations;
    }


    public List<GuestModel>  getGuestDetails(){
        Iterable<Guest> guests= this.guestRepository.findAll();

        Map<Long, GuestModel> guestModelMap= new HashMap<>();

        guests.forEach( guest -> {
            GuestModel model= new GuestModel();
            model.setGuestId(guest.getId());
            model.setGuestName(guest.getName());
            model.setGuestLastName(guest.getLastName());
            model.setGuestEmail(guest.getEmailAddress());
            model.setGuestAddress(guest.getAddress());
            model.setGuestCountry(guest.getCountry());
            model.setGuestState(guest.getState());
            model.setGuestPhoneNumber(guest.getPhoneNumber());
            guestModelMap.put(guest.getId(), model);
        });

        Iterable<Room> rooms = this.roomRepository.findAll();

        Map<Long, Room> roomMap= new HashMap<>();

        rooms.forEach(room -> {
            roomMap.put(room.getId(), room);
        });

        Iterable<Reservation> reservations= this.reservationRepository.findAll();

        reservations.forEach(reservation-> {
            if(guestModelMap.containsKey(reservation.getGuestId())){
                GuestModel model= guestModelMap.get(reservation.getGuestId());
                model.setReservationDate(reservation.getDate());
                if(roomMap.containsKey(reservation.getRoomId())){
                    model.setRoomName(roomMap.get(reservation.getRoomId()).getName());
                }
            }
        });

        List<GuestModel> guestModels= new ArrayList<>(guestModelMap.values());

        guestModels.sort(new Comparator<GuestModel>() {
            @Override
            public int compare(GuestModel o1, GuestModel o2) {
                String s1= o1.getGuestName()+o1.getGuestLastName(),
                        s2= o1.getGuestName()+o2.getGuestLastName();
                return s1.compareTo(s2);
            }
        });
        return guestModels;
    }

    public Guest addGuest(Guest guest) {
        if (null == guest){
            throw new RuntimeException("Guest is null !!");
        }
        return this.guestRepository.save(guest);
    }

    public List<Room> getRooms(){
        List<Room> rooms= new ArrayList<>();
        this.roomRepository.findAll().forEach( room -> rooms.add(room));
        return rooms;
    }
}

