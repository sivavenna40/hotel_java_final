package com.feuji.controller;

import java.util.List;
import java.util.Optional;

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

import com.feuji.dto.Rent;
import com.feuji.service.RentService;

@RestController
@RequestMapping("/rent")
public class RentController {

	@Autowired
	private RentService rentService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveRent(@RequestBody Rent rent) {
		rentService.saveRent(rent);
		return ResponseEntity.ok().body("Rent saved successfully");
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rent> getRent(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(rentService.getRentById(id));
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Rent>> getRentes() {
		List<Rent> rents = rentService.getRents();
		return ResponseEntity.ok().body(rents);
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteRent(@PathVariable("id") int id) {
		rentService.deleteRentById(id);
		return ResponseEntity.ok().body("Rent deleted successfully");
	}

	@GetMapping("/bybranchandtype/{branchId}/{roomType}")
	public ResponseEntity<Rent> findByBranchAndRoomType(@PathVariable("branchId") int branchId,
			@PathVariable("roomType") String roomType) {
		Rent rent = null;
		Optional<Rent> optionalRent = rentService.findByBranchAndRoomType(branchId, roomType);
		if(optionalRent.isPresent()) {
			rent=optionalRent.get();
		}
		return ResponseEntity.ok().body(rent);
	}
}
