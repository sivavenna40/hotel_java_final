package com.feuji.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.HotelRepository;
import com.feuji.dto.Hotel;
import com.feuji.service.HotelService;

@Service
public class HotelSeviceImplementation implements HotelService {

	@Autowired
	HotelRepository hotelRepository;

	@Override
	public String saveHotel(Hotel hotel) {
		if (findByHotelNameAndCity(hotel.getHotelName(), hotel.getCity()).equals(Optional.empty())) {
			hotelRepository.save(hotel);
			return "hotel details are saved";
		}
		return "hotel already exists";
	}

	@Override
	public Hotel getHotelById(int id) {
		Hotel hotel=null;
		Optional<Hotel> optionalHotel = hotelRepository.findById(id);
		if(optionalHotel.isPresent()) {
			hotel=optionalHotel.get();
		}
		return hotel;
	}

	@Override
	public List<Hotel> getHotels() {
		return hotelRepository.findAll();
	}

	@Override
	public void deleteHotel(int id) {
		hotelRepository.deleteById(id);
	}

	@Override
	public Optional<Hotel> findByHotelNameAndCity(String hotelName, String city) {
		return hotelRepository.findByHotelNameAndCity(hotelName, city);
	}

}
