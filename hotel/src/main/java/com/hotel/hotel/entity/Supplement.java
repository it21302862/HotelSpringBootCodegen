
package com.hotel.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

/**
 * create table for supplements
 */
@Table(name="Supplement")

public class Supplement {

    /**
     * initialize attributes
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int supplementID;


    @NotNull(message = "Season name must not be blank")
    private String supplementName;




    public Supplement() {
    }

    public Supplement(int supplementID, String supplementName, HotelContract hotelContract, Set<SupplementPrice> supplementPrices) {
        this.supplementID = supplementID;
        this.supplementName = supplementName;
        this.hotelContract = hotelContract;
        this.supplementPrices = supplementPrices;

    }

    //map with contracts
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contractID")
    private HotelContract hotelContract;

    //map with supplementPrices
    @JsonIgnore
    @OneToMany(mappedBy = "supplement")
    private Set<SupplementPrice> supplementPrices = new HashSet<>();

    public int getSupplementID() {
        return supplementID;
    }

    public void setSupplementID(int supplementID) {
        this.supplementID = supplementID;
    }

    public String getSupplementName() {
        return supplementName;
    }

    public void setSupplementName(String supplementName) {
        this.supplementName = supplementName;
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


}
