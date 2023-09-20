package com.hotel.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roomType")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roomTypeID;

    @NotNull(message = "room type cannot be a null value")
    private String roomType;


    private int noOfRooms;


    private int maxAdults;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contractID")
    private HotelContract hotelContract;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType")
    private Set<RoomTypePrice> roomTypePrices = new HashSet<>();


    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Set<RoomTypePrice> getRoomTypePrices() {return roomTypePrices;}

    public void setRoomTypePrices(Set<RoomTypePrice> roomTypePrices) {this.roomTypePrices = roomTypePrices;}

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Integer noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public int getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(Integer maxAdults) {
        this.maxAdults = maxAdults;
    }

    public RoomType() {
    }

    public RoomType(int roomTypeID, int noOfRooms, int maxAdults,String roomType, HotelContract hotelContract, Set<RoomTypePrice> roomTypePrices) {
        this.roomTypeID = roomTypeID;
        this.noOfRooms=noOfRooms;
        this.maxAdults=maxAdults;
        this.roomType = roomType;
        this.hotelContract = hotelContract;
        this.roomTypePrices = roomTypePrices;
    }

    public HotelContract getHotelContract() {
        return hotelContract;
    }

    public void setHotelContract(HotelContract hotelContract) {
        this.hotelContract = hotelContract;
    }
}
