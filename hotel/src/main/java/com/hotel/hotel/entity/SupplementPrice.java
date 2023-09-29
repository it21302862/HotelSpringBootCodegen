package com.hotel.hotel.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


@Entity
@Table(name = "supplement_price")
public class SupplementPrice {


    @EmbeddedId
    private SupplementPriceId supplementPriceId;

    //we can give multiple supplementPrices seasons with the same supplement name
    @JsonIgnore
    @ManyToOne
    @MapsId("seasonID")
    @JoinColumn(name = "season_id", insertable = false, updatable = false)
    private Season season;

    //we can give multiple supplementPrices with the same supplement name
    @JsonIgnore
    @ManyToOne
    @MapsId("supplementID")
    @JoinColumn(name = "supplement_id", insertable = false, updatable = false)
    private Supplement supplement;

    //map with contracts
    @JsonIgnore
    @ManyToOne
    @MapsId("contractID")
    @JoinColumn(name = "contract_id", insertable = false, updatable = false)
    private HotelContract hotelContract;




    @NotNull(message = "Price cannot be null")
    private double price;

    public SupplementPrice() {
    }

    public SupplementPrice(SupplementPriceId supplementPriceId, double price, Season season, Supplement supplement) {
        this.supplementPriceId = supplementPriceId;
        this.price = price;
        this.season = season;
        this.supplement = supplement;
    }

    public SupplementPriceId getSupplementPriceId() {
        return supplementPriceId;
    }

    public void setSupplementPriceId(SupplementPriceId supplementPriceId) {this.supplementPriceId = supplementPriceId;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Supplement getSupplement() {
        return supplement;
    }

    public void setSupplement(Supplement supplement) {
        this.supplement = supplement;
    }



    public HotelContract getHotelContract() {
        return hotelContract;
    }

    public void setHotelContract(HotelContract hotelContract) {
        this.hotelContract = hotelContract;
    }



    @Embeddable
    public static class SupplementPriceId  implements Serializable {
        private int seasonID;
        private int supplementID;
        private int contractID;

        public SupplementPriceId() {
        }

        public SupplementPriceId(int seasonId, int supplementId, int contractId) {
            this.seasonID = seasonId;
            this.supplementID = supplementId;
            this.contractID = contractId;
        }

        public int getSeasonID() {
            return seasonID;
        }

        public void setSeasonID(int seasonID) {
            this.seasonID = seasonID;
        }

        public int getSupplementID() {
            return supplementID;
        }

        public void setSupplementID(int supplementID) {
            this.supplementID = supplementID;
        }

        public int getContractID() {
            return contractID;
        }

        public void setContractID(int contractID) {
            this.contractID = contractID;
        }
    }
}

