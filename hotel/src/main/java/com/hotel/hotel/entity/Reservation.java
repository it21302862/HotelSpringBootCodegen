package com.hotel.hotel.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationID;

//    @NotNull(message = "status cannot be null")
    private ReservationType type;

    @JsonSerialize(using = CustomDateSerializer.class)
    @NotNull(message = "booking date cannot be null")
    private Date bookingDate;

    @JsonSerialize(using = CustomDateSerializer.class)
    @NotNull(message = "checkIn date cannot be null")
    private Date checkIn;

    @JsonSerialize(using = CustomDateSerializer.class)
    @NotNull(message = "checkOut date cannot be null")
    private Date checkOut;

    @NotNull()
    private int bookedRooms;

    private double roomPrice;

    private double finalPrice;

    private double supplementPriceWithNoOfDates;

    private double roomPriceWithNoOfDates;

    private double MarkupPrice;


// todo mapings

    private int contract;
    private int seasonId;

    private double discountPrice;

    public int noOfPax;

    @ManyToMany
    @JoinTable(
            name = "reservation_supplement_mapping",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "supplement_id")
    )
    private Set<Supplement> supplements = new HashSet<>();


    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
    }



    public double getSupplementPrice() {

        double totalSupplementPrice = 0.0;

        for (Supplement supplement : supplements) {
            for (SupplementPrice supplementPrice : supplement.getSupplementPrices()) {
                totalSupplementPrice += supplementPrice.getPrice();
                System.out.println("Supplement: " + supplement.getSupplementName() + ", Price: " + supplementPrice.getPrice());
            }
        }

        return totalSupplementPrice;
    }

    public int getNoOfNights() {
        LocalDate startDate = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long noOfNights = ChronoUnit.DAYS.between(startDate, endDate);
        return (int) noOfNights;
    }



    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    public Reservation() {
    }

    public Reservation(Date bookingDate, Date checkIn, Date checkOut, int bookedRooms) {
        this.bookingDate = bookingDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookedRooms = bookedRooms;
    }


    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }


    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(int bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }



    public Set<Supplement> getSupplements() {return supplements;}

    public void setSupplements(Set<Supplement> supplements) {this.supplements = supplements;}




    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }


    public int getContract() {return contract;}

    public void setContract(int contract) {this.contract = contract;}

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public double getSupplementPriceWithNoOfDates() {
        return supplementPriceWithNoOfDates;
    }

    public void setSupplementPriceWithNoOfDates(double supplementPriceWithNoOfDates) {
        this.supplementPriceWithNoOfDates = supplementPriceWithNoOfDates;
    }

    public double getRoomPriceWithNoOfDates() {
        return roomPriceWithNoOfDates;
    }

    public void setRoomPriceWithNoOfDates(double roomPriceWithNoOfDates) {
        this.roomPriceWithNoOfDates = roomPriceWithNoOfDates;
    }

    public double getMarkupPrice() {
        return MarkupPrice;
    }

    public void setMarkupPrice(double markupPrice) {
        MarkupPrice = markupPrice;
    }

    public int getNoOfPax() {
        return noOfPax;
    }

    public void setNoOfPax(int noOfPax) {
        this.noOfPax = noOfPax;
    }
}
