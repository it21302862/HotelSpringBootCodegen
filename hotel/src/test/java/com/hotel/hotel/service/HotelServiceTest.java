package com.hotel.hotel.service;

import com.hotel.hotel.DTO.HotelDTO;
import com.hotel.hotel.DTO.RoomTypePriceSaveDTO;
import com.hotel.hotel.entity.*;
import com.hotel.hotel.repository.HotelRepository;
import com.hotel.hotel.repository.RoomTypePriceRepository;
import com.hotel.hotel.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class HotelServiceTest {

    @Mock private HotelRepository hotelRepository;
    @InjectMocks  private HotelService underTest;

    @Mock
    private RoomTypePriceRepository roomTypePriceRepository;


    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp(){
        underTest = new HotelService(hotelRepository, new ModelMapper());
    }

    @Test
    void getAllHotels() {
        //test
        underTest.getAllHotels();
        //then
        verify(hotelRepository).findAll();
    }

    @Test
    void saveHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelName("amaya");
        hotelDTO.setHotelEmail("ama@gmail.com");
        hotelDTO.setDescription("this is a new hotel");
        hotelDTO.setImgUrl("https://images.pexels.com/photos/2844474/pexels-photo-2844474.jpeg?auto=compress&cs=tinysrgb&w=600");
        hotelDTO.setHotelPhoneNumber("0765678948");

        //Mock the hotelRepository
        when(hotelRepository.existsById(hotelDTO.getHotelID())).thenReturn(false);

        // Call the saveHotel method
        String result = underTest.saveHotel(hotelDTO);

        verify(hotelRepository).existsById(hotelDTO.getHotelID());

        verify(hotelRepository).save(any(Hotel.class));
        assertEquals(VarList.RSP_SUCCESS,result);
    }

    @Test
    void updateHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelID(1);
        hotelDTO.setHotelName("amaya");
        hotelDTO.setHotelEmail("ama@gmail.com");
        hotelDTO.setDescription("this is a new hotel");
        hotelDTO.setImgUrl("https://images.pexels.com/photos/2844474/pexels-photo-2844474.jpeg?auto=compress&cs=tinysrgb&w=600");
        hotelDTO.setHotelPhoneNumber("0765678948");

        //Mock the hotelRepository
        when(hotelRepository.existsById(hotelDTO.getHotelID())).thenReturn(true);

        //Call the update hotel method
        String result=underTest.updateHotel(hotelDTO);
        verify(hotelRepository).save(any(Hotel.class));//save method was called with any Hotel object

        //Assert the result
        assertEquals(VarList.RSP_SUCCESS,result);



    }

    @Test
    void searchHotel_Exists() {

        int existingHotelID=1;
        //set properties
        Hotel hotel=new Hotel(
                1,"Amaya","Amaya@gmail.com",
                "Colombo","0765678947","This is a new hotel",
                "https://images.pexels.com/photos/2844474/pexels-photo-2844474.jpeg?auto=compress&cs=tinysrgb&w=600"
        );

        // Mock the hotelRepository behavior
        when(hotelRepository.existsById(existingHotelID)).thenReturn(true);
        when(hotelRepository.findById(existingHotelID)).thenReturn(Optional.of(hotel));

        // Call the searchHotel method
        HotelDTO result = underTest.searchHotel(existingHotelID);
        verify(hotelRepository).existsById(existingHotelID);

        verify(hotelRepository).findById(existingHotelID);
        assertNotNull(result);

    }

    @Test
    void searchHotel_NotExists() {

        int nonExistingHotelID=2;

        // Mock the hotelRepository behavior
        when(hotelRepository.existsById(nonExistingHotelID)).thenReturn(false);

        // Call the searchHotel method
        HotelDTO result = underTest.searchHotel(nonExistingHotelID);
        verify(hotelRepository).existsById(nonExistingHotelID);

        verify(hotelRepository,never()).findById(anyInt());
        assertNull(result);

    }

    @Test
    void testDeleteHotel_Exists() {
        int hotelID=123;
        when(hotelRepository.existsById(hotelID)).thenReturn(true);

        String result = underTest.deleteHotel(hotelID);

        verify(hotelRepository).deleteById(hotelID);
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    void testDeleteSeason_NotExists() {
        int hotelID = 456; // Replace with a non-existent seasonID
        when(hotelRepository.existsById(hotelID)).thenReturn(false);

        String result = underTest.deleteHotel(hotelID);

        verify(hotelRepository, never()).deleteById(anyInt()); //deleteById is never called
        assertEquals(VarList.RSP_NO_DATA_FOUND, result);
    }

    @Test
    void getAvailableRoomTypesForHotel() throws ParseException {

        int hotelID=1;//valid hotelID;
        LocalDate currentDate=LocalDate.now();

        //create room type object
        RoomTypePrice roomTypePrice=new RoomTypePrice();

        //set the properties
        RoomType roomType = new RoomType();
        roomType.setRoomType("Single");
        roomType.setNoOfRooms(12);
        roomType.setMaxAdults(4);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2023-07-01"); // Replace with your desired date
        Date endDate = dateFormat.parse("2023-12-05");

        HotelContract hotelContract = new HotelContract();
        hotelContract.setStartDate(startDate);
        hotelContract.setEndDate(endDate);
        hotelContract.setTermsAndConditions("sample conditions");

        Season season=new Season();
        season.setSeasonName("Peak");
        season.setStartDate(Date.from(currentDate.minusDays(5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        season.setEndDate(Date.from(currentDate.plusDays(5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));


        roomTypePrice.setRoomType(roomType);
        roomTypePrice.setPrice(20000);
        roomTypePrice.setImgUrl("https://images.pexels.com/photos/2844474/pexels-photo-2844474.jpeg?auto=compress&cs=tinysrgb&w=600");
        roomTypePrice.setDescription("This is a new Room");
        roomTypePrice.setSeason(season);
        roomTypePrice.setHotelContract(hotelContract);

        List<RoomTypePrice> roomTypePriceList = new ArrayList<>();
        roomTypePriceList.add(roomTypePrice);

        MockitoAnnotations.openMocks(this);

        when(roomTypePriceRepository.findByHotel(hotelID)).thenReturn(roomTypePriceList);

        List<RoomTypePriceSaveDTO> result = underTest.getAvailableRoomTypesForHotel(hotelID);
        assertNotNull(result);
    }

    @Test
    @Disabled
    void calculatePriceWithAvailability() {
    }

    @Test
    @Disabled
    void getSupplementsByReservationId() {
    }

    @Test
    @Disabled
    void addSupplementToReservation() {
    }

    @Test
    @Disabled
    void calculateFinalPrice() {
    }

    @Test
    @Disabled
    void searchHotels() {
    }

    @Test
    @Disabled
    void getAllReservations() {
    }
}