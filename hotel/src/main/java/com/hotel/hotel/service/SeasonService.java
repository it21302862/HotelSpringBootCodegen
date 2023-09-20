package com.hotel.hotel.service;

import com.hotel.hotel.DTO.SeasonDTO;
import com.hotel.hotel.entity.Season;
import com.hotel.hotel.repository.SeasonRepository;
import com.hotel.hotel.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * service function for save hotels
     * @param seasonDTO
     * @return
     */
    public  String  saveSeason(SeasonDTO seasonDTO){

//        if(seasonRepo.existsBySeasonName(String.valueOf(seasonDTO.getSeasonName()))){
//
//            return VarList.RSP_DUPLICATED;
//        }
//        else{

            seasonRepository.save(modelMapper.map(seasonDTO, Season.class));
            return VarList.RSP_SUCCESS;
//        }
    }


    /**
     * service function for update season dates
     * @param seasonDTO
     * @return
     */
//    public String updateSeasondate(SeasonDTO seasonDTO){
//
//        if(seasonRepo.existsBySeasonName(String.valueOf(seasonDTO.getSeasonName()))){
//
//            seasonRepo.save(modelMapper.map(seasonDTO,Season.class));
//
//            return VarList.RSP_SUCCESS;
//
//        }
//        else {
//
//            return VarList.RSP_NO_DATA_FOUND;
//        }
//    }


    /**
     * get all seasons
     * @return
     */
//    public List<SeasonDTO> getAllSeasons(){
//
//        List<Season> seasonList = seasonRepo.findAll();
//        return modelMapper.map(seasonList,new TypeToken<ArrayList<SeasonDTO>>(){
//
//        }.getType());
//    }
}
