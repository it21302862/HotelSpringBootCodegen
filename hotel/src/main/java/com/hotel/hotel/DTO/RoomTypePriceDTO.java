package com.hotel.hotel.DTO;


import com.hotel.hotel.entity.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomTypePriceDTO {

    private String seasonName;

    private String roomType;

    private double price;

    private String description;

    private String imgUrl;



}
