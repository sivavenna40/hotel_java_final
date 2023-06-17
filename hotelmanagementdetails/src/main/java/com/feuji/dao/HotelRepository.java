package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	
	Optional<Hotel> findByHotelNameAndCity(String hotelName,String city);
}
