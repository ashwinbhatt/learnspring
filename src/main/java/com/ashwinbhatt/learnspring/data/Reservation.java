package com.ashwinbhatt.learnspring.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    private long id;

    @JoinColumn(name = "ROOM_ID")
    private Long roomId;

    @JoinColumn(name = "GUEST_ID")
    private Long guestId;

    @Column(name = "RES_DATE")
    private Date reservationDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Date getDate() {
        return reservationDate;
    }

    public void setDate(Date date) {
        this.reservationDate = date;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", roomId='" + roomId + '\'' +
                ", guestId='" + guestId + '\'' +
                ", date='" + reservationDate.toString() + '\'' +
                '}';
    }
}
