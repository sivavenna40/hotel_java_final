package com.feuji.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

import com.feuji.dto.RoomDetails;
import com.feuji.service.RoomDetailsService;

@RestController
@RequestMapping("/roomdetails")
public class RoomDetailsController {

	@Autowired
	private RoomDetailsService roomDetailsService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveRoomDetails(@RequestBody RoomDetails roomDetails) {

		roomDetailsService.saveRoomDetails(roomDetails);
		return ResponseEntity.ok().body("RoomDetails saved successfully");
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomDetails> getRoomDetails(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(roomDetailsService.findRoomDetails(id));
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDetails>> getRoomDetailses() {
		return ResponseEntity.ok().body(roomDetailsService.findAllRoomDetails());
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteRoomDetails(@PathVariable("id") int id) {
		roomDetailsService.deleteRoomDetailsById(id);
		return ResponseEntity.ok().body("RoomDetails deleted successfully");
	}

	@GetMapping("/{branchId}/{checkIn}/{checkOut}")
	public ResponseEntity<Map<String, Map<String, Number>>> getStatusCount(@PathVariable int branchId,
			@PathVariable String checkIn, @PathVariable String checkOut) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // specify the pattern of the string
		checkIn = checkIn.replace("\"", "");
		checkOut = checkOut.replace("\"", "");

		LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
		LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
		return ResponseEntity.ok().body(roomDetailsService.getStatusCount(branchId, checkInDate, checkOutDate));
	}

	@GetMapping(value = "/findavailablerooms/{branchId}/{checkIn}/{checkOut}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<RoomDetails>>> findByBranchAndRoomType(@PathVariable int branchId,
			@PathVariable String checkIn, @PathVariable String checkOut) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // specify the pattern of the string
		checkIn = checkIn.replace("\"", "");
		checkOut = checkOut.replace("\"", "");
		LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
		LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
			return ResponseEntity.ok().body(roomDetailsService.findAvalaibleRooms(branchId, checkInDate, checkOutDate));
	}
}
