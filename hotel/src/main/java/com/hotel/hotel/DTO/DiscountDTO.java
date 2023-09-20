package com.hotel.hotel.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscountDTO {

    private int discountID;

    private double discountPercentage;

    private int noOfDates;


}
