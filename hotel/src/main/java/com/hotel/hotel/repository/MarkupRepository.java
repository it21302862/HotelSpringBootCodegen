package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Markup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkupRepository extends JpaRepository<Markup,Markup.MarkupPercentageId> {


}
