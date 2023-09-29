package com.hotel.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

/**
 * create table for seasons
 */
@Table(name="Season")

public class Season {

    /**
     * initialize attributes
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seasonID;

    @NotBlank(message = "Season name must not be blank")
    private String seasonName;

    @NotNull(message = "Start date must not be null")
    private Date startDate;

    public Season() {
    }


    @NotNull(message = "End date must not be null")
    private Date endDate;

//    public Season(int seasonID, String seasonName, Date startDate, Date endDate, Set<SupplementPrice> supplementPrices, Set<Markup> markups) {
//        this.seasonID = seasonID;
//        this.seasonName = seasonName;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.supplementPrices = supplementPrices;
//        this.markups = markups;
//    }


    public Season(int seasonID, String seasonName, Date startDate, Date endDate, HotelContract hotelContract, Set<SupplementPrice> supplementPrices, Set<RoomTypePrice> roomTypePrices, Set<Markup> markups) {
        this.seasonID = seasonID;
        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotelContract = hotelContract;
        this.supplementPrices = supplementPrices;
        this.roomTypePrices = roomTypePrices;
        this.markups = markups;
    }

    //map with contracts
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contractID")
    private HotelContract hotelContract;

    //map with supplementPrices
    @JsonIgnore
    @OneToMany(mappedBy = "season")
    private Set<SupplementPrice> supplementPrices = new HashSet<>();


    //map with roomTypePrices
    @JsonIgnore
    @OneToMany(mappedBy = "season")
    private Set<RoomTypePrice> roomTypePrices = new HashSet<>();


    //map with markups
    @JsonIgnore
    @OneToMany(mappedBy = "season")
    private Set<Markup> markups = new HashSet<>();

    public int getSeasonID() {
        return seasonID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }



    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public HotelContract getHotelContract() {
        return hotelContract;
    }

    public void setHotelContract(HotelContract hotelContract) {
        this.hotelContract = hotelContract;
    }

    public Set<SupplementPrice> getSupplementPrices() {
        return supplementPrices;
    }

    public void setSupplementPrices(Set<SupplementPrice> supplementPrices) {
        this.supplementPrices = supplementPrices;
    }

    public Set<Markup> getMarkups() {
        return markups;
    }


    public void setMarkups(Set<Markup> markups) {
        this.markups = markups;
    }

    public Set<RoomTypePrice> getRoomTypePrices() {return roomTypePrices;}

    public void setRoomTypePrices(Set<RoomTypePrice> roomTypePrices) {this.roomTypePrices = roomTypePrices;}

//    public Markup getMarkups() {
//        return markups;
//    }
//
//    public void setMarkups(Markup markups) {
//        this.markups = markups;
//    }

}
