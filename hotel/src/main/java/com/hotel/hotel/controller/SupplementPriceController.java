package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.service.SupplementPriceService;
import com.hotel.hotel.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/hotels")
@CrossOrigin
public class SupplementPriceController {

    @Autowired
    private SupplementPriceService supplementPriceService;
    @Autowired
    private ResponseDTO responseDTO;

    /**
     * get all supplements
     * @return AllSupplements
     */
    @GetMapping("/supplementPrices")
    public ResponseEntity getAllSupplement(){

        try{

            List<Map<String, Object>> supplementDTOList = supplementPriceService.getAllSupplementPricesWithContractID();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(supplementDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        }catch (Exception ex){

            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
