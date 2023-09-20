package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.HotelDTO;
import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.DTO.RoomTypePriceDTO;
import com.hotel.hotel.DTO.RoomTypePriceSaveDTO;
import com.hotel.hotel.entity.*;
import com.hotel.hotel.repository.HotelRepository;
import com.hotel.hotel.service.HotelService;
import com.hotel.hotel.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Configuration
@EnableWebMvc
@RestController
@RequestMapping(value="api/v1/hotels")
@CrossOrigin
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private HotelRepository hotelRepository;

    /**
     * get all rooms stored within database
     * @return
     */
    @GetMapping("/hotels")
    public ResponseEntity getAllHotels() {
        try {
            List<HotelDTO> hotelDTOList= hotelService.getAllHotels();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(hotelDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * save hotels in database
     * @param hotelDTO
     * @return
     */
    @PostMapping("/hotel")
    public ResponseEntity saveHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            String res = hotelService.saveHotel(hotelDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("06")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Hotel Registered");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * search hotels
     * @param hotelID
     * @return
     */
    @GetMapping(value = "/searchHotel/{hotelID}")
    public ResponseEntity searchHotel(@PathVariable int hotelID) {
        try {
            HotelDTO hotelDTO= hotelService.searchHotel(hotelID);
            if(hotelDTO!=null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Hotel Available For this hotelID");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * update hotels
     * @param hotelDTO
     * @return
     */
    @PutMapping(value = "/updateHotel")
    public ResponseEntity updateHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            String res = hotelService.updateHotel(hotelDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("01")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Not a Registered Hotel");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * remove hotels from the database
     * @param hotelID
     * @return
     */
    @DeleteMapping("/deleteHotel/{hotelID}")
    public ResponseEntity deleteHotel(@PathVariable int hotelID){
        try {
            String res = hotelService.deleteHotel(hotelID);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Hotel Available For this hotelID");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * hotel can get all rooms that stored within that hotel
     * @param hotelID
     * @return
     */
//    @GetMapping("/hotel/{hotelID}/available-room-types")
//    public List<RoomTypePriceDTO> getAvailableRoomTypesForSelectedHotel(@PathVariable int hotelID) {
//        return hotelService.getAvailableRoomTypesForHotel(hotelID);
//    }

    @GetMapping("/hotel/{hotelID}/available-room-types")
    public List<RoomTypePriceSaveDTO> getAvailableRoomTypesForSelectedHotel(@PathVariable int hotelID) {
        return hotelService.getAvailableRoomTypesForHotel(hotelID);
    }



    // todo: make this POST request--->done
    // rename request
    @PostMapping("/calculate-price")
    public Map<String, Object> calculatePrice(
            // todo: introduce DTO
            @RequestParam int contractId,
            @RequestParam int roomTypeId,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam int noOfPax
    ) throws ParseException {
        return hotelService.calculatePriceWithAvailability(contractId, roomTypeId, checkIn, checkOut, noOfPax);
    }

    // todo: introduce a new API [GET]-->done
    // to retrieve supplements

    @GetMapping("/{reservationId}/supplements")
    public ResponseEntity<List<Supplement>> getSupplementsByReservationId(@PathVariable int reservationId) {
        try {
            List<Supplement> supplements = hotelService.getSupplementsByReservationId(reservationId);
            return new ResponseEntity<>(supplements, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // todo: save supplements [POST]
    // req obj
    // - reservation id
    // - supplement ids
    // to retrieve supplements


    @PostMapping("/{reservationID}/add-supplement")
    public ResponseEntity<String> addSupplementToReservation(
            @PathVariable int reservationID,
            @RequestParam List<Integer> supplementIDs // Use a list to accept multiple supplement IDs
    ) {
        Reservation reservation = null;

        for (int supplementID : supplementIDs) {
            reservation = hotelService.addSupplementToReservation(reservationID, supplementID);
        }

        if (reservation != null) {
            double totalValue = reservation.getSupplementPrice();

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Supplement price displayed.");
            responseDTO.setContent(totalValue);

            return new ResponseEntity(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode(VarList.RSP_FAIL);
            responseDTO.setMessage("Failed to display supplement display.");
            responseDTO.setContent(null);

            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/calculateFinalPrice/{reservationId}")
    public ResponseEntity<Map<String, Object>> calculateFinalPrice(@PathVariable int reservationId) {
        Map<String, Object> result = hotelService.calculateFinalPrice(reservationId);

        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }



    // todo: pay booking [POST]
    // req, reservation id
    // call service: booking payment;

    /**
     * customer search field
     * @param location
     * @param hotelName
     * @return
     */
//    @GetMapping("/hotelsFilter")
//    public List<Hotel> searchHotels(@RequestParam(required = false) String location, @RequestParam(required = false) String hotelName) {
//        return hotelService.searchHotels(location, hotelName);
//    }

    @GetMapping("/hotelsFilter")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String hotelName) {

        try {
            List<Hotel> hotels = hotelService.searchHotels(location, hotelName);

            if (hotels.isEmpty()) {
                // Handle the case where no hotels were found
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Hotel Available For this hotel name and location");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(hotels);
        } catch (Exception ex) {
            // Handle your custom exception
            return (ResponseEntity<List<Hotel>>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }




}
