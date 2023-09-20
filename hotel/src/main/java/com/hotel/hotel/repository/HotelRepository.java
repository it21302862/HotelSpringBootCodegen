package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {

//    @Query(value ="SELECT * FROM Hotel WHERE ID = ?1 AND ADDRESS =?2 ",nativeQuery = true)
//    Hotel getUserByIDAndAddress(String hotelAddress,String hotelName);


    Optional<Hotel> findByHotelName(String name);

    @Query(value="SELECT * FROM Hotel WHERE location LIKE %:location% AND hotel_name LIKE %:name%", nativeQuery = true)
    List<Hotel> findByAddressAndName(String location, String name);

}
