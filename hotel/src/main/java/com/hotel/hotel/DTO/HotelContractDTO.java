package com.hotel.hotel.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hotel.hotel.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelContractDTO {

    private int contractID;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startDate;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date endDate;
    private String termsAndConditions;

    private HotelDTO hotel;
    private Set<SeasonDTO> seasons;
    private Set<SupplementDTO> supplements;
    private Set<SupplementPriceDTO> supplementPrices;

    private Set<MarkupDTO> markupPrices;

    private DiscountDTO discounts;

    private Set<RoomTypeDTO> roomTypes;

    private Set<RoomTypePriceDTO> roomTypePrices;
}
