package com.hotel.hotel.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
/**
 * create table for contracts
 */
@Table(name="Hotel_Contract")
public class HotelContract {

    /**
     * initialize attributes
     */

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contractID;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotNull(message = "Terms and conditions are required")
    @Column(length = 1000)
    private String termsAndConditions;

    @ManyToOne
    @JoinColumn(name = "hotelID")
    private Hotel hotel;


    public HotelContract() {
    }

    //map with seasons
    @OneToMany(mappedBy = "hotelContract", cascade = CascadeType.ALL)
    private Set<Season> seasons = new HashSet<>();

    //map with discounts
    @OneToOne(mappedBy = "hotelContract", cascade = CascadeType.ALL)
    private Discount discounts;

    //map with supplements
    @OneToMany(mappedBy = "hotelContract", cascade = CascadeType.ALL)
    private Set<Supplement> supplements = new HashSet<>();

    //map with roomTypes
    @OneToMany(mappedBy = "hotelContract", cascade = CascadeType.ALL)
    private Set<RoomType> roomTypes = new HashSet<>();




    public HotelContract(int contractID, Date startDate, Date endDate, String termsAndConditions, Hotel hotel, Set<Season> seasons, Discount discounts, Set<Supplement> supplements, Set<RoomType> roomTypes, Set<SupplementPrice> supplementPrices, Set<Markup> markups, Set<RoomTypePrice> roomTypePrices) {
        this.contractID = contractID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termsAndConditions = termsAndConditions;
        this.hotel = hotel;
        this.seasons = seasons;
        this.discounts = discounts;
        this.supplements = supplements;
        this.roomTypes = roomTypes;
        this.supplementPrices = supplementPrices;
        this.markups = markups;
        this.roomTypePrices = roomTypePrices;
    }

    //create add season method to store seasons in contract
    public void addSeason(Season season) {
        season.setHotelContract(this);
        seasons.add(season);
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    //create add supplement method to store supplements in contract
    public void addSupplement(Supplement supplement) {
        supplement.setHotelContract(this);
        supplements.add(supplement);
    }

    public Set<Supplement> getSupplements(){
        return supplements;
    }

    //create add roomType method to store roomTypes in contract
    public void addRoomType(RoomType roomType) {
        roomType.setHotelContract(this);
        roomTypes.add(roomType);
    }

    public Set<RoomType> getRoomTypes() {return roomTypes;}

    //create add discount method to store discount in contract
    public void addDiscount(Discount discount) {
        if (discount != null) {
            if (this.discounts != null) {
                this.discounts.setHotelContract(null);
            }
            this.discounts = discount;
            discount.setHotelContract(this);
        }
    }

    public Discount getDiscounts() {return discounts;}

    //map with supplementPrices
    @JsonIgnore
    @OneToMany(mappedBy = "hotelContract", cascade = CascadeType.ALL)
    private Set<SupplementPrice> supplementPrices = new HashSet<>();

    //map with markups
    @JsonIgnore
    @OneToMany(mappedBy = "hotelContract",cascade = CascadeType.ALL)
    private Set<Markup> markups=new HashSet<>();

    //map with roomTypePrices
    @JsonIgnore
    @OneToMany(mappedBy = "hotelContract",cascade = CascadeType.ALL)
    private Set<RoomTypePrice> roomTypePrices=new HashSet<>();

    public Set<RoomTypePrice> getRoomTypePrices() {return roomTypePrices;}

    public Set<SupplementPrice> getSupplementPrices() {
        return supplementPrices;
    }

    public Set<Markup> getMarkups() {return markups;}

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public void setDiscounts(Discount discounts) {this.discounts = discounts;}

    public void setRoomTypes(Set<RoomType> roomTypes) {this.roomTypes = roomTypes;}

    public void setSupplements(Set<Supplement> supplements) {
        this.supplements = supplements;
    }

    public void setMarkups(Set<Markup> markups) {this.markups = markups;}

    public void setRoomTypePrices(Set<RoomTypePrice> roomTypePrices) {this.roomTypePrices = roomTypePrices;}

    public void setSupplementPrices(Set<SupplementPrice> supplementPrices) {
        this.supplementPrices = supplementPrices;
    }

    public void addSupplementPrice(SupplementPrice supplementPrice) {
        supplementPrice.setHotelContract(this);
        supplementPrices.add(supplementPrice);
    }

    public void addMarkup(Markup markup) {
        markup.setHotelContract(this);
        markups.add(markup);
    }

    public void addRoomTypePrice(RoomTypePrice roomTypePrice) {
        roomTypePrice.setHotelContract(this);
        roomTypePrices.add(roomTypePrice);
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }



    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }



}
