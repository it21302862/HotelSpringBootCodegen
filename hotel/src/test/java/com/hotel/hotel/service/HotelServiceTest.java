package com.hotel.hotel.service;

import com.hotel.hotel.DTO.HotelDTO;
import com.hotel.hotel.DTO.RoomTypePriceSaveDTO;
import com.hotel.hotel.entity.*;
import com.hotel.hotel.repository.*;
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

    @Mock
    private SupplementRepository supplementRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelContractRepository hotelContractRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private MarkupRepository markupRepository;

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
    void getSupplementsByReservationId() {

        int reservationId = 123;

        Supplement supplement1 = new Supplement();
        supplement1.setSupplementID(1); // Set the ID or other properties as needed
        supplement1.setSupplementName("Supplement 1");

        Supplement supplement2 = new Supplement();
        supplement2.setSupplementID(2); // Set the ID or other properties as needed
        supplement2.setSupplementName("Supplement 2");

        List<Supplement> expectedSupplements = new ArrayList<>();
        expectedSupplements.add(supplement1);
        expectedSupplements.add(supplement2);

        underTest.setSupplementRepository(supplementRepository);
        when(supplementRepository.getSupplementsBySeason(reservationId)).thenReturn(expectedSupplements);
        List<Supplement> actualSupplements = underTest.getSupplementsByReservationId(reservationId);
        assertEquals(expectedSupplements, actualSupplements);
    }

    @Test
    void addSupplementToReservation() {
        int reservationId = 1; // Replace with a valid reservation ID
        int supplementId = 2;// Replace with a valid supplement ID

        Reservation mockReservation = new Reservation();
        Supplement mockSupplement = new Supplement();

        underTest.setSupplementRepository(supplementRepository);
        underTest.setReservationRepository(reservationRepository);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));
        when(supplementRepository.findById(supplementId)).thenReturn(Optional.of(mockSupplement));

        Reservation result = underTest.addSupplementToReservation(reservationId, supplementId);

        assertNotNull(result);
        assertSame(mockReservation, result); // Verify that the same Reservation object is returned
        assertTrue(mockReservation.getSupplements().contains(mockSupplement)); // Verify that the supplement is added to the reservation
        verify(reservationRepository, times(1)).save(mockReservation);
    }

    @Test
    @Disabled
    void calculateFinalPrice() {
    }

    @Test
    void searchHotels() {
        String location = "Colombo";
        String hotelName= "Amaya";

        List<Hotel> mockHotels = new ArrayList<>();
        Hotel mockHotel1 = new Hotel();
        mockHotel1.setHotelID(1);
        mockHotel1.setLocation("Colombo");
        mockHotel1.setHotelName("Amaya");

        Hotel mockHotel2 = new Hotel();
        mockHotel2.setHotelID(2);
        mockHotel1.setLocation("Colombo");
        mockHotel1.setHotelName("Amaya Beach Hotel");

        mockHotels.add(mockHotel1);
        mockHotels.add(mockHotel2);

        when(hotelRepository.findByAddressAndName(location, hotelName)).thenReturn(mockHotels);

        List<Hotel> result = underTest.searchHotels(location, hotelName);

        assertNotNull(result);
        assertEquals(2, result.size()); //one hotel is returned
        assertEquals(mockHotel1, result.get(0)); // Ensure that the mock hotel object is returned
        verify(hotelRepository, times(1)).findByAddressAndName(location, hotelName);
    }

}