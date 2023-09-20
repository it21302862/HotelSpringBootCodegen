package com.hotel.hotel.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * create table for markups
 */
@Entity
@Table(name="markup")
public class Markup {

    /**
     * initialize attributes
     */
    @EmbeddedId
    private MarkupPercentageId markupPercentageId;


    //1:1 with season
    @JsonIgnore
    @ManyToOne
    @MapsId("seasonID")
    @JoinColumn(name="season_id", insertable = false, updatable = false)
    private Season season;

    //same contractID repeat with 1:M
    @JsonIgnore
    @ManyToOne
    @MapsId("contractID")
    @JoinColumn(name = "contract_id", insertable = false, updatable = false)
    private HotelContract hotelContract;

    @NotNull(message = "Markup percentage cannot be null")
    private double percentage;


    public Markup() {
    }


    public Markup(MarkupPercentageId markupPercentageId, Season season, double percentage) {
        this.markupPercentageId = markupPercentageId;
        this.season = season;
        this.percentage = percentage;
    }

    public MarkupPercentageId getMarkupPercentageId() {return markupPercentageId;}

    public void setMarkupPercentageId(MarkupPercentageId markupPercentageId) {
        this.markupPercentageId = markupPercentageId;
    }

    public double getPercentage() {return percentage;}

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Season getSeason() {return season;}

    public void setSeason(Season season) {this.season = season;}

    public HotelContract getHotelContract() {return hotelContract;}

    public void setHotelContract(HotelContract hotelContract) {
        this.hotelContract = hotelContract;
    }



    @Embeddable
    public static class MarkupPercentageId implements Serializable{

        private int seasonID;

        private int contractID;

        public MarkupPercentageId() {
        }

        public MarkupPercentageId(int seasonID, int contractID) {
            this.seasonID = seasonID;
            this.contractID = contractID;
        }

        public int getSeasonID() {
            return seasonID;
        }

        public void setSeasonID(int seasonID) {
            this.seasonID = seasonID;
        }

        public int getContractID() {
            return contractID;
        }

        public void setContractID(int contractID) {
            this.contractID = contractID;
        }
    }
}
