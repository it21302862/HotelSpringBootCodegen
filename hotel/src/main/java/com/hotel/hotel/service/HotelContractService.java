package com.hotel.hotel.service;

import com.hotel.hotel.DTO.*;
import com.hotel.hotel.entity.*;
import com.hotel.hotel.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelContractService {

    @Autowired
    private HotelContractRepository hotelContractRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SupplementPriceRepository supplementPriceRepo;

    @Autowired
    private MarkupRepository markupRepository;

    @Autowired
    private RoomTypePriceRepository roomTypePriceRepository;


    public void saveContract(HotelContractDTO hotelContractDTO) {
        HotelContract hotelContract = new HotelContract();

        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelContractDTO.getHotel().getHotelID());
        hotelContract.setHotel(hotelOptional.orElseThrow());
        hotelContract.setStartDate(hotelContractDTO.getStartDate());
        hotelContract.setEndDate(hotelContractDTO.getEndDate());
        hotelContract.setTermsAndConditions(hotelContractDTO.getTermsAndConditions());

        // Add seasons and supplements to the contract
        for (Season seasonRequest : this.mapSeasons(hotelContractDTO.getSeasons())) {
            hotelContract.addSeason(seasonRequest);
        }

        for (Supplement supplementRequest : this.mapSupplements(hotelContractDTO.getSupplements())) {
            hotelContract.addSupplement(supplementRequest);
        }

        //Add room types to the contract
        for(RoomType roomTypeRequest: this.mapRoomTypes(hotelContractDTO.getRoomTypes())){
            hotelContract.addRoomType(roomTypeRequest);
        }

        //Add discount to the contract
        Discount discountRequest= this.mapDiscount(hotelContractDTO.getDiscounts());
           hotelContract.addDiscount(discountRequest);


        // Save the contract with added seasons and supplements
        var savedContract = hotelContractRepository.save(hotelContract);

        var savedSeasons = new HashMap<String, Season>();
        for (Season season : savedContract.getSeasons()) {
            savedSeasons.put(season.getSeasonName(), season);
        }

        var savedSupplements = new HashMap<String, Supplement>();
        for (Supplement supplement : savedContract.getSupplements()) {
            savedSupplements.put(supplement.getSupplementName(), supplement);
        }

        var savedRoomTypes=new HashMap<String,RoomType>();
        for (RoomType roomType : savedContract.getRoomTypes()) {
            savedRoomTypes.put(roomType.getRoomType(), roomType);
        }

        // Step 2
        // Save Supplement Prices
        Set<SupplementPrice> supplementPrices = new HashSet<>();
        for (SupplementPriceDTO supplementPriceDTO : hotelContractDTO.getSupplementPrices()) {
            var supplementDAO = savedSupplements.get(supplementPriceDTO.getSupplementName());
            var seasonDAO = savedSeasons.get(supplementPriceDTO.getSeasonName());

            SupplementPrice supplementPrice = new SupplementPrice();
            if (supplementDAO != null && seasonDAO != null) {
                supplementPrice.setPrice(supplementPriceDTO.getPrice());
                supplementPrice.setSupplement(supplementDAO);
                supplementPrice.setHotelContract(savedContract);
                supplementPrice.setSeason(seasonDAO);
                supplementPrice.setSupplementPriceId(new SupplementPrice.SupplementPriceId(
                        seasonDAO.getSeasonID(),
                        supplementDAO.getSupplementID(),
                        savedContract.getContractID())
                );

                supplementPrices.add(supplementPrice);
            }
        }
        this.supplementPriceRepo.saveAll(supplementPrices);


        //Step 3
        //Save Markup Prices
        Set<Markup> markups=new HashSet<>();
        for(MarkupDTO markupDTO:hotelContractDTO.getMarkupPrices()){
            var seasonDAO=savedSeasons.get(markupDTO.getSeasonName());


            if(seasonDAO!=null){
                Markup markup=new Markup();
                markup.setPercentage(markupDTO.getPercentage());
                markup.setHotelContract(savedContract);
                markup.setSeason(seasonDAO);
                markup.setMarkupPercentageId(new Markup.MarkupPercentageId(
                        seasonDAO.getSeasonID(),
                        savedContract.getContractID())
                );

                markups.add(markup);
            }
        }
        this.markupRepository.saveAll(markups);


        //Step 4
        //Save RoomType Prices

        // Step 2
        // Save Supplement Prices
        Set<RoomTypePrice> roomTypePrices = new HashSet<>();
        for (RoomTypePriceDTO roomTypePriceDTO : hotelContractDTO.getRoomTypePrices()) {
            var roomTypeDAO = savedRoomTypes.get(roomTypePriceDTO.getRoomType());
            var seasonDAO = savedSeasons.get(roomTypePriceDTO.getSeasonName());

            RoomTypePrice roomTypePrice = new RoomTypePrice();
            if (roomTypeDAO != null && seasonDAO != null) {
                roomTypePrice.setPrice(roomTypePriceDTO.getPrice());
                roomTypePrice.setDescription(roomTypePriceDTO.getDescription());
                roomTypePrice.setImgUrl(roomTypePriceDTO.getImgUrl());
                roomTypePrice.setRoomType(roomTypeDAO);
                roomTypePrice.setHotelContract(savedContract);
                roomTypePrice.setSeason(seasonDAO);
                roomTypePrice.setRoomTypePriceId(new RoomTypePrice.RoomTypePriceId(
                        seasonDAO.getSeasonID(),
                        roomTypeDAO.getRoomTypeID(),
                        savedContract.getContractID())
                );

                roomTypePrices.add(roomTypePrice);
            }
        }
        this.roomTypePriceRepository.saveAll(roomTypePrices);



    }

    private Iterable<? extends Supplement> mapSupplements(Set<SupplementDTO> supplements) {
        return supplements.stream().map(supplementDTO -> {
            var supplement = new Supplement();
            supplement.setSupplementName(supplementDTO.getSupplementName());
            return supplement;
        }).collect(Collectors.toSet());
    }

    private Iterable<? extends Season> mapSeasons(Set<SeasonDTO> seasons) {
        return seasons.stream().map( seasonDTO -> {
            var season = new Season();
            season.setSeasonName(seasonDTO.getSeasonName());
            season.setStartDate(seasonDTO.getStartDate());
            season.setEndDate(seasonDTO.getEndDate());
            return season;
        } ).collect(Collectors.toSet());
    }

    private Iterable<? extends RoomType>mapRoomTypes(Set<RoomTypeDTO> roomTypes){
        return roomTypes.stream().map(RoomTypeDTO->{
            var roomType=new RoomType();
            roomType.setRoomType(RoomTypeDTO.getRoomType());
            roomType.setNoOfRooms(RoomTypeDTO.getNoOfRooms());
            roomType.setMaxAdults(RoomTypeDTO.getMaxAdults());
            return roomType;

        }).collect(Collectors.toSet());
    }

    private Discount mapDiscount(DiscountDTO discountDTO) {
        if (discountDTO != null) {
            Discount discount = new Discount();
            discount.setDiscountPercentage(discountDTO.getDiscountPercentage());
            discount.setNoOfDates(discountDTO.getNoOfDates());
            return discount;
        }
        return null;
    }



    public HotelContract getContractById(int contractId) {
        return hotelContractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with ID: " + contractId));
    }


    public HotelContractService(HotelContractRepository hotelContractRepository) {
        this.hotelContractRepository = hotelContractRepository;
    }

    public List<HotelContractDTO> getAllContracts() {
        // Fetch hotel contracts from the repository
        List<HotelContract> contracts = hotelContractRepository.findAll();

        // Convert the contracts to DTOs
        List<HotelContractDTO> contractDTOs = contracts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return contractDTOs;
    }

    private HotelContractDTO convertToDTO(HotelContract contract) {
        HotelContractDTO contractDTO = new HotelContractDTO();

        // Map other properties from HotelContract to HotelContractDTO
        contractDTO.setContractID(contract.getContractID());
        contractDTO.setStartDate(contract.getStartDate());
        contractDTO.setEndDate(contract.getEndDate());
        contractDTO.setTermsAndConditions(contract.getTermsAndConditions());

        // Fetch the associated Hotel entity
        Optional<Hotel> hotelOptional = hotelRepository.findById(contract.getHotel().getHotelID());

        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();

            // Create a HotelDTO and set its properties
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setHotelID(hotel.getHotelID()); // Set the ID or any other relevant property
            // Set other properties of hotelDTO as needed

            // Set the hotelDTO in the contractDTO
            contractDTO.setHotel(hotelDTO);
        }

        return contractDTO;
    }



}