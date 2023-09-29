package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.DTO.SeasonDTO;
import com.hotel.hotel.service.SeasonService;
import com.hotel.hotel.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/hotels")
@CrossOrigin
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private ResponseDTO responseDTO;





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
    @GetMapping("/seasons")
    public ResponseEntity getAllSeasons(){

        try{

            List<Map<String, Object>> seasonDTOList = seasonService.getAllSeasons();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(seasonDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        }catch (Exception ex){

            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * remove seasons
     * @param seasonID
     * @return
     */
    @DeleteMapping("/deleteSeason/{seasonID}")
    public ResponseEntity deleteSeason(@PathVariable int seasonID){
        try {
            String res = seasonService.deleteSeason(seasonID);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Successfully removed season");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No season Available For this seasonID");
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
