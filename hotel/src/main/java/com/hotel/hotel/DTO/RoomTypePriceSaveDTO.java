package com.hotel.hotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomTypePriceSaveDTO {

    private String seasonName;

    private String roomType;

    private double price;

    private String description;

    private String imgUrl;

    private int contractID;

    private int roomTypeID;

    private int seasonID;
}
