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

import com.feuji.dto.RoomType;
import com.feuji.service.RoomTypeService;

@RestController
@RequestMapping("/roomtype")
public class RoomTypeController {

	@Autowired
	private RoomTypeService roomTypeService;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveRoomType(@RequestBody RoomType roomtype) {

		roomTypeService.saveRoomType(roomtype);
		return ResponseEntity.ok().body("RoomType saved successfully");
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomType> getRoomType(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(roomTypeService.getRoomType(id));
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomType>> getRoomTypees() {
		return ResponseEntity.ok().body(roomTypeService.getRoomTypes());
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteRoomType(@PathVariable("id") int id) {
		roomTypeService.deleteRoomType(id);
		return ResponseEntity.ok().body("RoomType deleted successfully");
	}
}
