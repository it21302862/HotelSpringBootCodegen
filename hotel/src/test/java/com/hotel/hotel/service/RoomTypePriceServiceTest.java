package com.hotel.hotel.service;

import com.hotel.hotel.DTO.RoomTypePriceDTO;
import com.hotel.hotel.entity.HotelContract;
import com.hotel.hotel.entity.RoomType;
import com.hotel.hotel.entity.RoomTypePrice;
import com.hotel.hotel.entity.Season;
import com.hotel.hotel.repository.RoomTypePriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RoomTypePriceServiceTest {

    @Mock
    private RoomTypePriceRepository roomTypePriceRepository;

    @Mock
    private ModelMapper modelMapper;
    private RoomTypePriceService underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new RoomTypePriceService(roomTypePriceRepository, new ModelMapper());
    }

    @Test
    void TestGetAllRoomTypePrices() {
        // Create a sample RoomTypePrice object
        RoomTypePrice roomTypePrice = new RoomTypePrice();
        HotelContract hotelContract = new HotelContract();
        Season season = new Season();
        RoomType roomType = new RoomType();

        roomTypePrice.setHotelContract(hotelContract);
        roomTypePrice.setSeason(season);
        roomTypePrice.setRoomType(roomType);

        RoomTypePriceDTO roomTypePriceDTO=new RoomTypePriceDTO();

        when(modelMapper.map(roomTypePrice, RoomTypePriceDTO.class)).thenReturn(roomTypePriceDTO);
        List<RoomTypePrice> roomTypePriceList = new ArrayList<>();
        roomTypePriceList.add(roomTypePrice);

        when(roomTypePriceRepository.findAll()).thenReturn(roomTypePriceList);
        List<Map<String, Object>> result = underTest.getAllRoomTypePrices();

        assertEquals(1, result.size());
        Map<String, Object> roomTypePriceData = result.get(0);
        assertEquals(roomTypePriceDTO, roomTypePriceData.get("roomTypePriceDTO"));






    }
}