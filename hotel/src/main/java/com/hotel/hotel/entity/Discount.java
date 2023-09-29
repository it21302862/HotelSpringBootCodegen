package com.hotel.hotel.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int discountID;

    @NotNull(message = "discount should have to be added to the contract")
    private double discountPercentage;


    @NotNull(message = "no of dates cannot be null")
    private int noOfDates;

    //map with contracts
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "contractID")
    private HotelContract hotelContract;

    public Discount() {
    }

    public Discount(int discountID, double discountPercentage, int noOfDates, HotelContract hotelContract) {
        this.discountID = discountID;
        this.discountPercentage = discountPercentage;
        this.noOfDates = noOfDates;
        this.hotelContract = hotelContract;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getNoOfDates() {
        return noOfDates;
    }

    public void setNoOfDates(int noOfDates) {
        this.noOfDates = noOfDates;
    }

    public HotelContract getHotelContract() {
        return hotelContract;
    }

    public void setHotelContract(HotelContract hotelContract) {
        this.hotelContract = hotelContract;
    }
}
