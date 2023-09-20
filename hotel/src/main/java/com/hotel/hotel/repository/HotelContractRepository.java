package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Hotel;
import com.hotel.hotel.entity.HotelContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HotelContractRepository extends JpaRepository<HotelContract,Integer> {


    @Query(nativeQuery = true, value = "select * from hotel_contract where hotelid = ?1 and start_date < now() and end_date > now();")
    List<HotelContract> findValidContractByHotel(int hotelID);

    @Query(value = "SELECT max_adults FROM room_type WHERE room_typeid = :roomTypeId", nativeQuery = true)
    int findMaxAdultsForRoomType(int roomTypeId);

    @Query(value = "SELECT no_of_rooms FROM room_type WHERE room_typeid = :roomTypeId", nativeQuery = true)
    int findRoomCountForRoomType(int roomTypeId);

    @Query(nativeQuery = true,value = "SELECT SUM(booked_rooms) FROM reservation where room_type_id=?1 ")
    Integer countBookedRooms(int roomTypeId);


}
