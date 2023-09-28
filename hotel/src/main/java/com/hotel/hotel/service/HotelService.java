package com.hotel.hotel.service;

import com.hotel.hotel.DTO.HotelDTO;
import com.hotel.hotel.DTO.RoomTypePriceDTO;
import com.hotel.hotel.DTO.RoomTypePriceSaveDTO;
import com.hotel.hotel.entity.*;
import com.hotel.hotel.repository.*;
import com.hotel.hotel.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelContractRepository hotelContractRepository;

    @Autowired
    private RoomTypePriceRepository roomTypePriceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private SupplementRepository supplementRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private MarkupRepository markupRepository;

    @Autowired
    private SupplementPriceRepository supplementPriceRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public HotelService(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Autowired // Add this annotation
    public void setSupplementRepository(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    @Autowired // Add this annotation
    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Autowired // Add this annotation
    public void setDiscountRepository(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Autowired
    public void setMarkupRepository(MarkupRepository markupRepository) {
        this.markupRepository = markupRepository;
    }


    @Autowired
    public void setHotelContractRepository(HotelContractRepository hotelContractRepository) {
        this.hotelContractRepository = hotelContractRepository;
    }

    @Autowired
    public void setRoomTypePriceRepository(RoomTypePriceRepository roomTypePriceRepository) {
        this.roomTypePriceRepository = roomTypePriceRepository;
    }

    @Autowired
    public void setRoomTypeRepository(RoomTypeRepository roomTypeRepository){
        this.roomTypeRepository =roomTypeRepository;
    }



    /**
     * saving hotels service function
     *
     * @param hotelDTO
     * @return
     */
    public String saveHotel(HotelDTO hotelDTO) {
        if (hotelRepository.existsById(hotelDTO.getHotelID())) {
            return VarList.RSP_DUPLICATED;
        } else {
            hotelRepository.save(modelMapper.map(hotelDTO, Hotel.class));
            return VarList.RSP_SUCCESS;
        }
    }

    /**
     * update hotels service function
     *
     * @param hotelDTO
     * @return
     */
    public String updateHotel(HotelDTO hotelDTO) {
        if (hotelRepository.existsById(hotelDTO.getHotelID())) {
            hotelRepository.save(modelMapper.map(hotelDTO, Hotel.class));
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    /**
     * get all hotels service function
     *
     * @return
     */
    public List<HotelDTO> getAllHotels() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return modelMapper.map(hotelList, new TypeToken<ArrayList<HotelDTO>>() {
        }.getType());
    }

    /**
     * search hotel service function
     *
     * @param hotelID
     * @return
     */
    public HotelDTO searchHotel(int hotelID) {
        if (hotelRepository.existsById(hotelID)) {
            Hotel hotel = hotelRepository.findById(hotelID).orElse(null);
            return modelMapper.map(hotel, HotelDTO.class);
        } else {
            return null;

        }
    }

    /**
     * remove hotel service function
     *
     * @param hotelID
     * @return
     */
    public String deleteHotel(int hotelID) {
        if (hotelRepository.existsById(hotelID)) {
            hotelRepository.deleteById(hotelID);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }


//    public List<RoomTypePriceDTO> getAvailableRoomTypesForHotel(int hotelID) {
//        LocalDate currentDate = LocalDate.now();
//
//        List<RoomTypePrice> roomTypePrices = roomTypePriceRepository.findByHotel(hotelID);
//
//        List<RoomTypePriceDTO> roomTypePriceDTOs = roomTypePrices.stream().map(roomTypePrice -> {
//            RoomTypePriceDTO roomTypePriceDTO = new RoomTypePriceDTO();
//            roomTypePriceDTO.setSeasonName(roomTypePrice.getSeason().getSeasonName());
//            roomTypePriceDTO.setRoomType(roomTypePrice.getRoomType().getRoomType());
//            roomTypePriceDTO.setPrice(roomTypePrice.getPrice());
//            roomTypePriceDTO.setImgUrl(roomTypePrice.getImgUrl());
//            roomTypePriceDTO.setDescription(roomTypePrice.getDescription());
//
//            return roomTypePriceDTO;
//        }).collect(Collectors.toList());
//
//        return roomTypePriceDTOs;
//    }


    public List<RoomTypePriceSaveDTO> getAvailableRoomTypesForHotel(int hotelID) {
        LocalDate currentDate = LocalDate.now();

        List<RoomTypePrice> roomTypePrices = roomTypePriceRepository.findByHotel(hotelID);

        List<RoomTypePriceSaveDTO> roomTypePriceDTOs = roomTypePrices.stream()
                .filter(roomTypePrice -> {
                    LocalDate seasonStartDate = roomTypePrice.getSeason().getStartDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(); // Convert Date to LocalDate
                    LocalDate seasonEndDate = roomTypePrice.getSeason().getEndDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(); // Convert Date to LocalDate
                    // Check if currentDate is within the season dates
                    return currentDate.isEqual(seasonStartDate) ||
                            (currentDate.isAfter(seasonStartDate) && currentDate.isBefore(seasonEndDate));
                })
                .map(roomTypePrice -> {
                    RoomTypePriceSaveDTO roomTypePriceDTO = new RoomTypePriceSaveDTO();
                    roomTypePriceDTO.setSeasonName(roomTypePrice.getSeason().getSeasonName());
                    roomTypePriceDTO.setRoomType(roomTypePrice.getRoomType().getRoomType());
                    roomTypePriceDTO.setPrice(roomTypePrice.getPrice());
                    roomTypePriceDTO.setImgUrl(roomTypePrice.getImgUrl());
                    roomTypePriceDTO.setDescription(roomTypePrice.getDescription());
                    roomTypePriceDTO.setContractID(roomTypePrice.getHotelContract().getContractID());
                    roomTypePriceDTO.setRoomTypeID(roomTypePrice.getRoomType().getRoomTypeID());
                    roomTypePriceDTO.setSeasonID(roomTypePrice.getSeason().getSeasonID());

                    return roomTypePriceDTO;
                })
                .collect(Collectors.toList());

        return roomTypePriceDTOs;
    }



    public Map<String, Object> calculatePriceWithAvailability(int contractId, int roomTypeId, String checkIn, String checkOut, int noOfPax) throws ParseException {
        Map<String, Object> result = new HashMap<>();

        int maxAdults = hotelContractRepository.findMaxAdultsForRoomType(roomTypeId);
        var roomCount = Math.round((float) noOfPax / maxAdults);

        int allRooms = hotelContractRepository.findRoomCountForRoomType(roomTypeId);
        Integer bookedRooms = hotelContractRepository.countBookedRooms(roomTypeId);

        int availableRooms = bookedRooms != null ? allRooms - bookedRooms : allRooms;

        if (availableRooms < roomCount) {
            String message = "Only " + availableRooms + " rooms available for now";
            System.out.println(message);
            result.put("error", message);
            return result; // Return a result indicating an error
        }


        List<RoomTypePrice> prices = roomTypePriceRepository.findBasePrice(contractId, roomTypeId, checkIn, checkOut);

        if (prices.size() > 0) {
            RoomTypePrice roomTypePrice = prices.get(0);
            var price = roomTypePrice.getPrice();
            double roomPrice = roomCount * price;

            // Fetch the RoomType based on roomTypeId
            RoomType roomType = roomTypeRepository.findById(roomTypeId).orElse(null);

            if (roomType == null) {
                result.put("error", "Room type not found");
                return result; // Return a result indicating an error
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date checkInDate = dateFormat.parse(checkIn);
            Date checkOutDate = dateFormat.parse(checkOut);

            // todo: save a price reservation-->done
            Reservation reservation = new Reservation(new Date(), checkInDate, checkOutDate, roomCount);
            reservation.setRoomType(roomType);
            reservation.setRoomPrice(roomPrice);
            reservation.setType(ReservationType.PRICE); // Set the type as PRICE
            reservation.setContract(contractId);
            reservation.setSeasonId(roomTypePrice.getSeason().getSeasonID());
            reservation.setNoOfPax(noOfPax);
            reservation.setBookedRooms(roomCount);
            reservationRepository.save(reservation);


            // todo:done
            // return object including
            // - price reservation id
            // - room price
            //           return roomPrice;
            // Add the price reservation ID and room price to the result
            result.put("reservationId", reservation.getReservationID());
            result.put("roomPrice", roomPrice);
            result.put("bookedRooms",roomCount);
            return result;
        }

        System.out.println(prices);

        result.put("error", "No prices found");
        return result; // Return a result indicating an error
    }

    // todo: service: retrieve supplements by reservation id-->done
    // - get contract id from reservation
    // - return supplements from contract

    public List<Supplement> getSupplementsByReservationId(int reservationId) {

        List<Supplement> supplements=supplementRepository.getSupplementsBySeason(reservationId);

        return supplements;
    }


    // todo: service: save supplements-->done;
    // - add supplements to reservation
    // - add discount to reservation
    // - calculate markup
    // - transform PRICE reservation => IN_PROGRESS reservation
    // - calculate final price
    // - save final price to the reservation
    // - return final price


    public Reservation addSupplementToReservation(int reservationId, int supplementId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Supplement supplement = supplementRepository.findById(supplementId).orElse(null);

        if (reservation != null && supplement != null) {

            reservation.addSupplement(supplement); // Add the supplements
            reservationRepository.save(reservation);
        }

        return reservation;
    }


    public Map<String, Object> calculateFinalPrice(int reservationId) {
        Map<String, Object> result = new HashMap<>();

        // Retrieve the reservation based on its ID
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation == null) {
            result.put("error", "Reservation not found");
            return result;
        }

        // Calculate the base room price based on reservation details
        double roomPrice = reservation.getRoomPrice();


        // Retrieve the associated HotelContract
        int contractID = reservation.getContract();
        Discount discount = discountRepository.findById(contractID).orElse(null);

        LocalDate bookingLocalDate = reservation.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkInLocalDate = reservation.getCheckIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long daysDifference = ChronoUnit.DAYS.between(bookingLocalDate, checkInLocalDate);
        if (daysDifference >= discount.getNoOfDates()) {
            reservation.setDiscountPrice(roomPrice * (discount.getDiscountPercentage())); // Calculate discount price
        }

        double discountPrice = reservation.getDiscountPrice();

        // Calculate the supplement price
        double supplementPrice = reservation.getSupplementPrice();
        double roomPriceWithNoOfDates=roomPrice*reservation.getNoOfNights();
        double supplementPriceWithNoOfDates=supplementPrice*reservation.getNoOfNights()*reservation.getNoOfPax();

        // Calculate the final price by adding room price, discount price, and supplement price
        double totalPrice = roomPriceWithNoOfDates - discountPrice + supplementPriceWithNoOfDates;
        System.out.println(totalPrice);

        //get the markup price
        int seasonID=reservation.getSeasonId();
        Markup markup = markupRepository.findById(new Markup.MarkupPercentageId(seasonID, contractID)).orElse(null);

        double markupPrice=markup.getPercentage();
        System.out.println(markupPrice);
        double MarkupPrice=totalPrice*markupPrice;
        double finalPrice=totalPrice+MarkupPrice;

        reservation.setFinalPrice(finalPrice);
        reservation.setDiscountPrice(discountPrice);
        reservation.setRoomPriceWithNoOfDates(roomPriceWithNoOfDates);
        reservation.setSupplementPriceWithNoOfDates(supplementPriceWithNoOfDates);
        reservation.setMarkupPrice(MarkupPrice);
        reservation.setType(ReservationType.IN_PROGRESS);

        result.put("finalPrice", finalPrice);
        result.put("supplementPriceWithNoOfDates",supplementPriceWithNoOfDates);
        result.put("roomPriceWithNoOfDates",roomPriceWithNoOfDates);
        result.put("discountPrice",discountPrice);
        result.put("MarkupPrice",MarkupPrice);


        return result;
    }



    // todo: service: booking payment;
        // - transform IN_PROGRESS reservation => PAYED reservation

        /**
         * search hotels via address and name
         * @param location
         * @param hotelName
         * @return
         */
        public List<Hotel> searchHotels (String location, String hotelName){
            return hotelRepository.findByAddressAndName(location, hotelName);
        }


        public List<Reservation> getAllReservations(){
            List<Reservation> reservationList=reservationRepository.findAll();
            return modelMapper.map(reservationList, new TypeToken<ArrayList<Reservation>>() {
            }.getType());

        }


    }
