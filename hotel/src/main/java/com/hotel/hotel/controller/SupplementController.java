package com.hotel.hotel.controller;

import com.hotel.hotel.DTO.ResponseDTO;
import com.hotel.hotel.DTO.SupplementDTO;
import com.hotel.hotel.service.SupplementService;
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
public class SupplementController {

    @Autowired
    private SupplementService supplementService;

    @Autowired
    private ResponseDTO responseDTO;




    /**
     * update supplements
     * @param supplementDTO
     * @return
     */
//    @PutMapping(value = "/updateSupplement")
//    public ResponseEntity updateSupplement(@RequestBody SupplementDTO supplementDTO){
//
//        try{
//            String res = supplementService.updateSupplement(supplementDTO);
//
//            if(res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(supplementDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//
//            } else if (res.equals("01")) {
//
//                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("Supplement still not in the system");
//                responseDTO.setContent(supplementDTO);
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
     * get all supplements
     * @return
     */

    @GetMapping("/supplements")
    public ResponseEntity getAllSupplement(){

        try{

            List<Map<String, Object>> supplementDTOList = supplementService.getAllSupplement();
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

    /**
     * search supplements
     * @param supplementId
     * @return
     */
//    @GetMapping("searchSupplement/{supplementId}")
//    public ResponseEntity searchSupplement(@PathVariable int supplementId){
//
//        try {
//            SupplementDTO supplementDTO = supplementService.searchSupplement(supplementId);
//            if (supplementDTO !=null) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(supplementDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else {
//                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("No supplimant Available For this Id");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(e.getMessage());
//            responseDTO.setContent(e);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * remove supplements
     * @param supplementId
     * @return
     */

    @DeleteMapping("/deleteSupplement/{supplementID}")
    public ResponseEntity deleteSupplement(@PathVariable int supplementID){

        try {
            String res = supplementService.deleteSupplement(supplementID);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Successfully removed supplement");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No supplement Available For this supplement id");
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
     * supplements attach to seasons
     * @param supplementID
     * @param seasonName
     * @return
     */
//    @PutMapping("/{supplementID}/supplement/{seasonName}")
//    public ResponseEntity<Supplement> assignSeasonToSupplement(
//            @PathVariable int supplementID,
//            @PathVariable String seasonName
//    ) {
//        Supplement assignedSupplement = supplementService.assignSeasonToSupplement(supplementID, seasonName);
//        if (assignedSupplement != null) {
//            return new ResponseEntity<>(assignedSupplement, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


}
