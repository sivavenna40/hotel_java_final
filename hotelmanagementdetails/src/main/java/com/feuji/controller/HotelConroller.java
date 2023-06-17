package com.feuji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.dto.Branch;
import com.feuji.dto.Hotel;
import com.feuji.service.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelConroller {

	@Autowired
	HotelService hotelService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveHotel(@RequestBody Hotel hotel) {
		return ResponseEntity.ok().body(hotelService.saveHotel(hotel));
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getRoomType(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(hotelService.getHotelById(id));
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> getRoomTypees() {
		return ResponseEntity.ok().body(hotelService.getHotels());
	}
	@GetMapping(value = "/getbranches/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Branch>> getBranches(@PathVariable("hotelId") int hotelId) {
		return ResponseEntity.ok().body(hotelService.getHotelById(hotelId).getBranches());
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteBranch(@PathVariable("id") int id) {
		hotelService.deleteHotel(id);
		return ResponseEntity.ok().body("hotel deleted successfully");
	}
}
