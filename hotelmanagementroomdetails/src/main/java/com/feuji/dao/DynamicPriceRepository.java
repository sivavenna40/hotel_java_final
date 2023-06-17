package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.dto.DynamicPrice;

public interface DynamicPriceRepository extends JpaRepository<DynamicPrice, Integer>{
	
		@Query(value="SELECT  rent_change FROM hotel_managment.dynamic_price where upper_bound<=?1 and lower_bound>=?2;",nativeQuery = true)
		Optional<DynamicPrice>findByUpperBoundLowerBound(int upperBound,int lowerBound);
}
