package com.hotel.hotel.repository;

import com.hotel.hotel.entity.RoomTypePrice;
import com.hotel.hotel.entity.SupplementPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplementPriceRepository extends JpaRepository<SupplementPrice, SupplementPrice.SupplementPriceId> {

}
