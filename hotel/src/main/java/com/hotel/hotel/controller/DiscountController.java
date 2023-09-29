package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.service.DiscountService;
import com.hotel.hotel.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/hotels")
@CrossOrigin
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ResponseDTO responseDTO;


    /**
     * get discount
     */


    @GetMapping("/discounts")
    public ResponseEntity getAllDiscounts(){

        try{

            List<Map<String, Object>> discountList = discountService.getAllDiscounts();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(discountList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        }catch (Exception ex){

            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * remove discounts
     * @param discountID
     * @return
     */

    @DeleteMapping("/deleteDiscount/{discountID}")
    public ResponseEntity deleteDiscount(@PathVariable int discountID){

        try {
            String res = discountService.deleteDiscount(discountID);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Successfully removed discount");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No discounts Available For this discount id");
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
