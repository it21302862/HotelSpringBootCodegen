package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query(nativeQuery=true,value="SELECT contract FROM reservation WHERE reservationID = :reservationId")
    Integer getContractIdByReservationId(@Param("reservationId") int reservationId);
}

