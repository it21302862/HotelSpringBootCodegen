package com.hotel.hotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTypeDTO {

    private int roomTypeID;

    private String roomType;

    private int noOfRooms;

    private int maxAdults;
}
