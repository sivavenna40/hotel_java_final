package com.feuji.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.DynamicRent;

public interface DynamicRentRepository extends JpaRepository<DynamicRent, Integer>{
		
//	@Query(value="SELECT rent FROM hotel_managment.dynamic_rent where and room_type=?1 and branch_id=?2 and booking_date=?3;",nativeQuery = true)
	Optional<DynamicRent> findByRoomTypeAndBranchIdAndBookingDate(String roomType,int branchId,LocalDate date);
	
}
