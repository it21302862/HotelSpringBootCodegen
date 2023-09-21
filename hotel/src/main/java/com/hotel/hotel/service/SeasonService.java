package com.hotel.hotel.service;

import com.hotel.hotel.DTO.SeasonDTO;
import com.hotel.hotel.entity.Season;
import com.hotel.hotel.repository.SeasonRepository;
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
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * get all seasons
     * @return
     */
//    public List<SeasonDTO> getAllSeasons(){
//
//        List<Season> seasonList = seasonRepository.findAll();
//        return modelMapper.map(seasonList,new TypeToken<ArrayList<SeasonDTO>>(){
//
//        }.getType());
//    }

    public List<Map<String, Object>> getAllSeasons(){

        List<Season> seasonList = seasonRepository.findAll();
        List<Map<String, Object>> seasonDataList = new ArrayList<>();

        for (Season season : seasonList) {
            SeasonDTO seasonDTO = modelMapper.map(season, SeasonDTO.class);
            int contractID = season.getHotelContract().getContractID();

            // Create a map to store the SeasonDTO and contractID
            Map<String, Object> seasonData = new HashMap<>();
            seasonData.put("seasonDTO", seasonDTO);
            seasonData.put("contractID", contractID);

            seasonDataList.add(seasonData);
        }

        return seasonDataList;
    }

}
