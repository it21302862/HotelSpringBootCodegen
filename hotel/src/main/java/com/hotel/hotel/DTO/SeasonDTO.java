package com.hotel.hotel.DTO;

import com.hotel.hotel.entity.HotelContract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeasonDTO {

    private int seasonID;
    private String seasonName;
    private Date startDate;
    private  Date endDate;
}
