package com.hotel.hotel.service;


import com.hotel.hotel.DTO.RoomTypeDTO;
import com.hotel.hotel.entity.RoomType;
import com.hotel.hotel.repository.RoomTypeRepository;
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
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<Map<String, Object>> getAllRoomTypesWithContractID() {
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        List<Map<String, Object>> roomTypeDataList = new ArrayList<>();

        for (RoomType roomType : roomTypeList) {
            RoomTypeDTO roomTypeDTO = modelMapper.map(roomType, RoomTypeDTO.class);
            int contractID = roomType.getHotelContract().getContractID();

            // Create a map to store the RoomTypeDTO and contractID
            Map<String, Object> roomTypeData = new HashMap<>();
            roomTypeData.put("roomTypeDTO", roomTypeDTO);
            roomTypeData.put("contractID", contractID);

            roomTypeDataList.add(roomTypeData);
        }

        return roomTypeDataList;
    }
}
