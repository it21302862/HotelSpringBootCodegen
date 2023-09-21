package com.hotel.hotel.service;


import com.hotel.hotel.DTO.SupplementPriceDTO;
import com.hotel.hotel.entity.SupplementPrice;
import com.hotel.hotel.repository.SupplementPriceRepository;
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
public class SupplementPriceService {

    @Autowired
    private SupplementPriceRepository supplementPriceRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Map<String, Object>> getAllSupplementPricesWithContractID() {
        List<SupplementPrice> supplementPriceList = supplementPriceRepository.findAll();
        List<Map<String, Object>> supplementPriceDataList = new ArrayList<>();

        for (SupplementPrice supplementPrice : supplementPriceList) {
            SupplementPriceDTO supplementPriceDTO = modelMapper.map(supplementPrice, SupplementPriceDTO.class);
            int contractID = supplementPrice.getHotelContract().getContractID();

            // Create a map to store the SupplementPriceDTO and contractID
            Map<String, Object> supplementPriceData = new HashMap<>();
            supplementPriceData.put("supplementPriceDTO", supplementPriceDTO);
            supplementPriceData.put("contractID", contractID);

            supplementPriceDataList.add(supplementPriceData);
        }

        return supplementPriceDataList;
    }

}
