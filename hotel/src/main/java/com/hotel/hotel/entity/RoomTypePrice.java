package com.hotel.hotel.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name="roomType_price")
public class RoomTypePrice {

    @EmbeddedId
    private RoomTypePriceId roomTypePriceId;

    @NotNull(message = "Price cannot be null")
    private double price;


    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Img Url cannot be null")
    private String imgUrl;



    //we can give multiple seasons with the same name
    @JsonIgnore
    @ManyToOne
    @MapsId("seasonID")
    @JoinColumn(name = "season_id", insertable = false, updatable = false)
    private Season season;

    //map roomTypes
    @JsonIgnore
    @ManyToOne
    @MapsId("roomTypeID")
    @JoinColumn(name = "roomtype_id", insertable = false, updatable = false)
    private RoomType roomType;

    //map contracts
    @JsonIgnore
    @ManyToOne
    @MapsId("contractID")
    @JoinColumn(name = "contract_id", insertable = false, updatable = false)
    private HotelContract hotelContract;

    public RoomTypePrice() {
    }

    public RoomTypePrice(RoomTypePriceId roomTypePriceId, double price, Season season, RoomType roomType,String description,String imgUrl) {
        this.roomTypePriceId = roomTypePriceId;
        this.price = price;
        this.season = season;
        this.roomType = roomType;
        this.description=description;
        this.imgUrl=imgUrl;
    }

    public RoomTypePriceId getRoomTypePriceId(RoomTypePriceId roomTypePriceId) {return this.roomTypePriceId;}

    public void setRoomTypePriceId(RoomTypePriceId roomTypePriceId) {this.roomTypePriceId = roomTypePriceId;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getImgUrl() {return imgUrl;}

    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}

    public Season getSeason() {return season;}

    public void setSeason(Season season) {this.season = season;}

    public RoomType getRoomType() {return roomType;}

    public void setRoomType(RoomType roomType) {this.roomType = roomType;}

    public HotelContract getHotelContract() {return hotelContract;}

    public void setHotelContract(HotelContract hotelContract) {this.hotelContract = hotelContract;}



    @Embeddable
    public static class RoomTypePriceId  implements Serializable {

        private int seasonID;

        private int roomTypeID;

        private int contractID;

        public RoomTypePriceId() {
        }

        public RoomTypePriceId(int seasonID, int roomTypeID, int contractID) {
            this.seasonID = seasonID;
            this.roomTypeID = roomTypeID;
            this.contractID = contractID;
        }

        public int getSeasonID() {
            return seasonID;
        }

        public void setSeasonID(int seasonID) {
            this.seasonID = seasonID;
        }

        public int getRoomTypeID() {
            return roomTypeID;
        }

        public void setRoomTypeID(int roomTypeID) {
            this.roomTypeID = roomTypeID;
        }

        public int getContractID() {
            return contractID;
        }

        public void setContractID(int contractID) {
            this.contractID = contractID;
        }
    }

}
