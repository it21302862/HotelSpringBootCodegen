package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.DTO.SeasonDTO;
import com.hotel.hotel.service.SeasonService;
import com.hotel.hotel.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotels")

public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private ResponseDTO responseDTO;


    //add season
    @PostMapping(value = "/season")
    public ResponseEntity saveSeason(@RequestBody SeasonDTO seasonDTO){

        try{
            String res = seasonService.saveSeason(seasonDTO);

            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(seasonDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("06")) {

                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("This season is already added");
                responseDTO.setContent(seasonDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
            else {

                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }

        }catch (Exception ex){

            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);


        }

    }


    /**
     * update season date
     * @param seasonDTO
     * @return
     */
//    @PutMapping(value = "/updateSeasonDate")
//    public ResponseEntity updateSeasonDate(@RequestBody SeasonDTO seasonDTO){
//
//        try{
//            String res = seasonService.updateSeasondate(seasonDTO);
//
//            if(res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(seasonDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//
//            } else if (res.equals("01")) {
//
//                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("season still not in the system");
//                responseDTO.setContent(seasonDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//
//            }
//            else {
//
//                responseDTO.setCode(VarList.RSP_FAIL);
//                responseDTO.setMessage("Error");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//
//            }
//
//        }catch (Exception ex){
//
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(ex.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//
//
//        }
//    }

    /**
     * get season details
     * @return
     */
//    @GetMapping("/seasons")
//    public ResponseEntity getAllSeasons(){
//
//        try{
//
//            List<SeasonDTO> seasonDTOList = seasonService.getAllSeasons();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(seasonDTOList);
//            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//
//        }catch (Exception ex){
//
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(ex.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }

}
