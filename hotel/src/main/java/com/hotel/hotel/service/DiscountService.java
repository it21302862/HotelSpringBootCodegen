package com.hotel.hotel.service;

import com.hotel.hotel.DTO.SupplementDTO;
import com.hotel.hotel.entity.Discount;
import com.hotel.hotel.entity.Supplement;
import com.hotel.hotel.repository.DiscountRepository;
import com.hotel.hotel.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Map<String, Object>> getAllDiscounts() {
        List<Discount> discountList = discountRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Discount discount : discountList) {
            Map<String, Object> discountMap = new HashMap<>();
            int contractID=discount.getHotelContract().getContractID();
            discountMap.put("discountID", discount.getDiscountID());
            discountMap.put("discountPercentage", discount.getDiscountPercentage());
            discountMap.put("noOfDates", discount.getNoOfDates());
            discountMap.put("contractID",contractID);

            result.add(discountMap);
        }

        return result;
    }

    public String deleteDiscount(int discountID) {
        if (discountRepository.existsById(discountID)) {
            discountRepository.deleteById(discountID);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }


}
