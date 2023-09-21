package com.hotel.hotel.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hotel.hotel.entity.CustomDateSerializer;
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
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startDate;
    @JsonSerialize(using = CustomDateSerializer.class)
    private  Date endDate;
}
