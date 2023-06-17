package com.feuji.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.dto.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
	@Query(value = "SELECT * FROM hotel_managment.booking WHERE (checkin_time BETWEEN ?1 AND ?2) OR (checkout_time BETWEEN ?1 AND ?2)", nativeQuery = true)
	Optional<List<Booking>> findByCheckOutTimeAndCheckInTime(LocalDate checkin,LocalDate checkout);

	@Query(value = "Select * from hotel_managment.booking where checkin_time <=?1 and checkout_time>=?1", nativeQuery = true)
	Optional<List<Booking>> findByCheckInTime(LocalDate checkin);
	
}
