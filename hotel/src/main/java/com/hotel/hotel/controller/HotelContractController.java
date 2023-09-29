package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.HotelContractDTO;
import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.entity.RoomType;
import com.hotel.hotel.repository.SeasonRepository;
import com.hotel.hotel.repository.SupplementRepository;
import com.hotel.hotel.service.HotelContractService;
import com.hotel.hotel.util.VarList;
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

    @Autowired
    private ResponseDTO responseDTO;

//    @Autowired
//    private SupplementRepository supplementRepo;


    /**
     * save contracts
     * @param hotelContractDTO
     * @return
     */
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

    /**
     * get contracts
     * @return
     */
        @GetMapping("/contracts")
        public ResponseEntity<List<HotelContractDTO>> getAllHotelContracts() {
            List<HotelContractDTO> contracts = hotelContractService.getAllContracts();
            return ResponseEntity.ok(contracts);
        }

    /**
     * remove contracts
      * @param contractID
     * @return
     */
    @DeleteMapping("/deleteContract/{contractID}")
    public ResponseEntity deleteContract(@PathVariable int contractID){
        try {
            String res = hotelContractService.deleteContract(contractID);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Contract Available For this contractID");
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
    }





