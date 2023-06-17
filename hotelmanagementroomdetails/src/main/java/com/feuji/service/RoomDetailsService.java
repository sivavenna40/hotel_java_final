package com.feuji.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.feuji.dto.Booking;
import com.feuji.dto.RoomDetails;

public interface RoomDetailsService {

	void saveRoomDetails(RoomDetails roomDetails);

	RoomDetails findRoomDetails(int id);

	List<RoomDetails> findAllRoomDetails();

	void deleteRoomDetails(int id);

	void deleteRoomDetailsById(int id);

	public List<RoomDetails> bookedRoomDetails(List<Booking> bookings, int branchId);

	public Map<String, Map<String, Number>> getStatusCount(int branchId, LocalDate checkIn, LocalDate checkOut);

	public Map<String, Map<String, Number>> getStatusCountByCheckInDate(int branchId,
			List<RoomDetails> bookedRoomDetails);

	Map<String, List<RoomDetails>> findAvalaibleRooms(int branchId, LocalDate checkIn, LocalDate checkOut);

}
