package com.hotel.hotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
    public class SupplementPriceDTO {
    private String seasonName;
    private String supplementName;
    private double price;
}
