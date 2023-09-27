package com.hotel.hotel.service;

import com.hotel.hotel.DTO.RoomTypeDTO;
import com.hotel.hotel.entity.HotelContract;
import com.hotel.hotel.entity.RoomType;
import com.hotel.hotel.repository.RoomTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomTypeServiceTest {

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    private RoomTypeService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoomTypeService(roomTypeRepository, modelMapper);
    }


    @Test
    void getAllRoomTypesWithContractID() {
        RoomType roomType = new RoomType();
        HotelContract hotelContract = new HotelContract();
        hotelContract.setContractID(1);

        roomType.setHotelContract(hotelContract);


        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        when(modelMapper.map(roomType, RoomTypeDTO.class)).thenReturn(roomTypeDTO);



        List<RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(roomType);

        when(roomTypeRepository.findAll()).thenReturn(roomTypeList);
        List<Map<String, Object>> result = underTest.getAllRoomTypesWithContractID();

        assertEquals(1, result.size());
        Map<String, Object> roomTypeData = result.get(0);
        assertEquals(roomTypeDTO, roomTypeData.get("roomTypeDTO"));
        assertEquals(1, roomTypeData.get("contractID"));



    }
}