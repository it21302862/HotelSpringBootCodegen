package com.hotel.hotel.repository;

import com.hotel.hotel.entity.RoomTypePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomTypePriceRepository extends JpaRepository<RoomTypePrice,RoomTypePrice.RoomTypePriceId> {
    // todo: rename function name
    @Query(nativeQuery = true, value = "select * from room_type_price inner join season " +
            "on contractid = contract_id " +
            "where contract_id = ?1 and roomtype_id = ?2 " +
            "and start_date < ?3 and end_date > ?3 and start_date < ?4 and end_date > ?4")
    List<RoomTypePrice> findBasePrice(int contractId, int roomTypeId, String startDate, String endDate);

    @Query(nativeQuery = true, value = "select * from room_type_price rtp " +
            "inner join hotel_contract ht on ht.contractid = rtp.contract_id " +
            "where ht.hotelid = ?1 and start_date < now() and end_date > now()")
    List<RoomTypePrice> findByHotel(int hotelID);
}
