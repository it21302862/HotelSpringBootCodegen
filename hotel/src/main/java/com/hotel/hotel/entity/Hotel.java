package com.hotel.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;


@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
/**
 * create table for store hotels
 */
@Table(name="Hotel")
public class Hotel {

    /**
     * creating hotel attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int hotelID;

    @NotBlank(message="You should need to give a name to the hotel")
    private String hotelName;



    @NotBlank(message = "Address cannot be null please give the location")
    private String location;


    @Column(unique = true)
    @NotBlank(message="Email address cannot be null")
    @Email(message="Invalid Email Address!Please try again!")
    private String hotelEmail;



    @NotNull(message = "Hotel Description Cannot be null")
    private String description;

    @NotBlank(message = "Contact information cannot be blank")
    @Pattern(regexp = "\\+?[0-9]{10}+",message = "Invalid Phone Number!Please Try Again!")

    private String hotelPhoneNumber;

    @NotNull(message = "Image Url Cannot be null")
    private String imgUrl;



    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private Set<HotelContract> hotelContracts;

    /**
     * initialize getters and setters
     * @return
     */
    public int getHotelID() {
        return hotelID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }


    public Hotel(int hotelID, String hotelName, String hotelEmail,String location, String hotelPhoneNumber,String description,String imgUrl) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.location = location;
        this.hotelEmail = hotelEmail;
        this.hotelPhoneNumber = hotelPhoneNumber;
        this.description = description;
        this.imgUrl=imgUrl;
    }

    public Hotel(){
    }


    public String getHotelEmail() {
        return hotelEmail;
    }

    public void setHotelEmail(String hotelEmail) {
        this.hotelEmail = hotelEmail;
    }

    public String getHotelPhoneNumber() {
        return hotelPhoneNumber;
    }

    public void setHotelPhoneNumber(String hotelPhoneNumber) {
        this.hotelPhoneNumber = hotelPhoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
