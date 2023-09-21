package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.HotelContractDTO;
import com.hotel.hotel.entity.RoomType;
import com.hotel.hotel.repository.SeasonRepository;
import com.hotel.hotel.repository.SupplementRepository;
import com.hotel.hotel.service.HotelContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/hotels")
@CrossOrigin
public class HotelContractController {

    @Autowired
    private HotelContractService hotelContractService;

    @Autowired
    private SeasonRepository seasonRepository;

//    @Autowired
//    private SupplementRepository supplementRepo;



    @PostMapping("/contract")
    public ResponseEntity<String> createContract(@RequestBody HotelContractDTO hotelContractDTO) {
        try {

            this.hotelContractService.saveContract(hotelContractDTO);

            return ResponseEntity.ok("Contract created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the contract: " + e.getMessage());
        }
    }

        @GetMapping("/contracts")
        public ResponseEntity<List<HotelContractDTO>> getAllHotelContracts() {
            List<HotelContractDTO> contracts = hotelContractService.getAllContracts();
            return ResponseEntity.ok(contracts);
        }
    }





