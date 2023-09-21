package com.hotel.hotel.service;

import com.hotel.hotel.DTO.RoomTypePriceDTO;
import com.hotel.hotel.entity.RoomTypePrice;
import com.hotel.hotel.repository.RoomTypePriceRepository;
import com.hotel.hotel.repository.SeasonRepository;
import com.hotel.hotel.repository.SupplementRepository;
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
public class RoomTypePriceService {

    @Autowired
    private RoomTypePriceRepository roomTypePriceRepository;


    @Autowired
    private ModelMapper modelMapper;

    public List<Map<String, Object>> getAllRoomTypePrices() {
        List<RoomTypePrice> roomTypePriceList = roomTypePriceRepository.findAll();
        List<Map<String, Object>> roomTypePriceDataList = new ArrayList<>();

        for (RoomTypePrice roomTypePrice : roomTypePriceList) {
            RoomTypePriceDTO roomTypePriceDTO = modelMapper.map(roomTypePrice, RoomTypePriceDTO.class);
            int contractID = roomTypePrice.getHotelContract().getContractID();
            int seasonID= roomTypePrice.getSeason().getSeasonID();
            int roomTypeID=roomTypePrice.getRoomType().getRoomTypeID();

            // Create a map to store the RoomTypePriceDTO and contractID
            Map<String, Object> roomTypePriceData = new HashMap<>();
            roomTypePriceData.put("roomTypePriceDTO", roomTypePriceDTO);
            roomTypePriceData.put("contractID", contractID);
            roomTypePriceData.put("seasonID", seasonID);
            roomTypePriceData.put("roomTypeID", roomTypeID);

            roomTypePriceDataList.add(roomTypePriceData);
        }

        return roomTypePriceDataList;
    }
}
