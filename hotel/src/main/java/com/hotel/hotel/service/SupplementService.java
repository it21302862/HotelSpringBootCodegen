package com.hotel.hotel.service;

import com.hotel.hotel.DTO.SupplementDTO;
import com.hotel.hotel.entity.Supplement;
import com.hotel.hotel.repository.SeasonRepository;
import com.hotel.hotel.repository.SupplementRepository;
import com.hotel.hotel.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SupplementService {

    @Autowired
    private SupplementRepository supplementRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ModelMapper modelMapper;

//    public String saveSupplement(SupplementDTO supplementDTO){
//
//        if(supplementRepository.existsById(supplementDTO.getSupplementID())){
//
//            return VarList.RSP_DUPLICATED;
//        }
//        else{
//
//            supplementRepository.save(modelMapper.map(supplementDTO, Supplement.class));
//            return VarList.RSP_SUCCESS;
//        }
//    }


//    public String updateSupplement(SupplementDTO supplementDTO){
//
//        if(supplementRepo.existsById(supplementDTO.getSupplementID())){
//
//            supplementRepo.save(modelMapper.map(supplementDTO,Supplement.class));
//
//            return VarList.RSP_SUCCESS;
//
//        }
//        else {
//
//            return VarList.RSP_NO_DATA_FOUND;
//        }
//    }

    public List<Map<String, Object>> getAllSupplement(){

        List<Supplement> supplementList = supplementRepository.findAll();
        List<Map<String, Object>> supplementDataList = new ArrayList<>();

        for (Supplement supplement : supplementList) {
            SupplementDTO supplementDTO = modelMapper.map(supplement, SupplementDTO.class);
            int contractID = supplement.getHotelContract().getContractID();

            // Create a map to store the SupplementDTO and contractID
            Map<String, Object> supplementData = new HashMap<>();
            supplementData.put("supplementDTO", supplementDTO);
            supplementData.put("contractID", contractID);

            supplementDataList.add(supplementData);
        }

        return supplementDataList;
    }

    public String deleteSupplement(int supplementID) {
        if (supplementRepository.existsById(supplementID)) {
            supplementRepository.deleteById(supplementID);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }



//    public SupplementDTO searchSupplement(int supplementNo ){
//        if(supplementRepo.existsById(supplementNo)){
//
//            Supplement supplement= supplementRepo.findById(supplementNo).orElse(null);
//            return modelMapper.map(supplement,SupplementDTO.class);
//        }
//
//        else{
//            return  null;
//        }
//    }



//    public String deleteSupplement(int supplementNo){
//        if (supplementRepo.existsById(supplementNo)){
//            supplementRepo.deleteById(supplementNo);
//            return VarList.RSP_SUCCESS;
//        }else {
//            return VarList.RSP_NO_DATA_FOUND;
//        }
//    }

//    public Supplement assignSeasonToSupplement(int supplementID, String seasonName) {
//
//        Set<Season> SeasonSet=null;
//
//        Optional<Supplement> optionalSupplement = supplementRepo.findById(supplementID);
//        Optional<Season> optionalSeason = seasonRepo.findById(seasonName);
//
//        if (optionalSupplement.isPresent() && optionalSeason.isPresent()) {
//            Supplement supplement = optionalSupplement.get();
//            Season season = optionalSeason.get();
//
//            SeasonSet=supplement.getSeasons();
//            SeasonSet.add(season);
//            supplement.setSeasons(SeasonSet);
//
//            return supplementRepo.save(supplement);
//        } else {
//
//            throw new EntityNotFoundException("Supplement or Season not found.");
//        }
//    }
}
