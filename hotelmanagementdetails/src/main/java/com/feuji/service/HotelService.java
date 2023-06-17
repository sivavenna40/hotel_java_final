package com.feuji.service;

import java.util.List;
import java.util.Optional;

import com.feuji.dto.Hotel;

public interface HotelService {
	
	String saveHotel(Hotel hotel);
	
	Hotel getHotelById(int id);
	
	List<Hotel> getHotels();
	
	void deleteHotel(int id);

	Optional<Hotel> findByHotelNameAndCity(String hotelName,String city);
}
