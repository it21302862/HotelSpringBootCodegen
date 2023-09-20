package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplementRepository extends JpaRepository<Supplement,Integer> {
    @Query(nativeQuery = true, value = "SELECT * from supplement s inner join hotelmanagement.supplement_price sp on sp.supplement_id = s.supplementid INNER JOIN hotelmanagement.reservation r ON r.season_id = sp.season_id WHERE r.reservationid = ?1")
    List<Supplement> getSupplementsBySeason(int reservationId);

}
