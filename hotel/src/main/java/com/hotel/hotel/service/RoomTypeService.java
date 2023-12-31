package com.hotel.hotel.service;


import com.hotel.hotel.DTO.RoomTypeDTO;
import com.hotel.hotel.entity.RoomType;
import com.hotel.hotel.repository.RoomTypeRepository;
import com.hotel.hotel.repository.SeasonRepository;
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
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public RoomTypeService(RoomTypeRepository roomTypeRepository, ModelMapper modelMapper) {
        this.roomTypeRepository = roomTypeRepository;
        this.modelMapper = modelMapper;
    }


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

    public String deleteRoomType(int roomTypeID) {
        if (roomTypeRepository.existsById(roomTypeID)) {
            roomTypeRepository.deleteById(roomTypeID);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
