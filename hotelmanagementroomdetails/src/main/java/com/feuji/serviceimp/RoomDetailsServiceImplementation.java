package com.feuji.serviceimp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.dao.DynamicRentRepository;
import com.feuji.dao.RoomDetailsRepository;
import com.feuji.dto.Booking;
import com.feuji.dto.DynamicRent;
import com.feuji.dto.RoomDetails;
import com.feuji.service.RoomDetailsService;

@Service
public class RoomDetailsServiceImplementation implements RoomDetailsService {
	private static String booked = "Booked";
	private static String available = "Available";

	@Autowired
	RoomDetailsRepository roomDetailsRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DynamicRentRepository dynamicRentRepository;

	@Value("${rest.bookingurl}")
	String bookingUrl;

	@Override
	public void saveRoomDetails(RoomDetails roomDetails) {
		roomDetailsRepository.save(roomDetails);
	}

	@Override
	public RoomDetails findRoomDetails(int id) {
		RoomDetails roomDetails=null;
		Optional<RoomDetails> optionalRoomDetail = roomDetailsRepository.findById(id);
		if(optionalRoomDetail.isPresent()){
			roomDetails=optionalRoomDetail.get();
		}
		return roomDetails;
	}

	@Override
	public List<RoomDetails> findAllRoomDetails() {
		return roomDetailsRepository.findAll();
	}

	@Override
	public void deleteRoomDetails(int id) {
		roomDetailsRepository.deleteById(id);
	}

	@Override
	public void deleteRoomDetailsById(int id) {
		roomDetailsRepository.deleteById(id);
	}

	// list of total rooms in a particular branch
	public List<RoomDetails> totalRoomDetialsByBranchId(int branchId) {
		return roomDetailsRepository.findAll().stream()
				.filter(room -> room.getRent().getBranch().getBranchid() == branchId).collect(Collectors.toList());
	}

	// mapping total rooms of particular branch to room type
	public Map<String, List<RoomDetails>> totalRoomDetailsMapByRoomType(List<RoomDetails> totalRoomDetailsList) {
		return totalRoomDetailsList.stream()
				.collect(Collectors.groupingBy(room -> room.getRent().getRoomType().getRoomType()));
	}

	// list of booked rooms in a particular branch
	public List<RoomDetails> bookedRoomDetailsByBranchIdAndCheckInAndCheckOut(int branchId, LocalDate checkIn,
			LocalDate checkOut) {
		Booking[] bookings = restTemplate.getForObject(bookingUrl + checkIn + "/" + checkOut, Booking[].class);
		List<Booking> list = new ArrayList<>();
		list.addAll(Arrays.asList(bookings));
		return list.stream()
				.map(booking -> roomDetailsRepository.findById(booking.getRoomDetails().getRoomId()).get())
				.filter(room -> room.getRent().getBranch().getBranchid() == branchId).collect(Collectors.toList());
	}

	public List<RoomDetails> bookedRoomDetails(List<Booking> bookings, int branchId) {
		return bookings.stream()
				.filter(booking -> booking.getRoomDetails().getRent().getBranch().getBranchid() == branchId)
				.map(Booking::getRoomDetails).collect(Collectors.toList());
	}

	public Map<String, List<RoomDetails>> bookedRoomDetailsMapByRoomType(List<RoomDetails> bookedRoomDetails) {
		return bookedRoomDetails.stream()
				.collect(Collectors.groupingBy(room -> room.getRent().getRoomType().getRoomType()));

	}

	// mapping available rooms of particular branch to room type
	public List<RoomDetails> getAvailableRooms(Map<String, List<RoomDetails>> bookedRoomDetailsMap,
			List<RoomDetails> totalRooms, Map<String, List<RoomDetails>> totalRoomDetailsMap, String roomType) {
		List<RoomDetails> bookedRooms = bookedRoomDetailsMap.get(roomType);
		List<RoomDetails> avaliableRooms;
		if (bookedRooms != null && !bookedRooms.isEmpty()) {
			avaliableRooms = totalRooms.stream().filter(b -> 
				!bookedRooms.contains(b)
			).collect(Collectors.toList());
		} else {
			avaliableRooms = totalRoomDetailsMap.get(roomType);
		}
		return avaliableRooms;
	}

	public Map<String, Map<String, Number>> getStatusCount(int branchId, LocalDate checkIn, LocalDate checkOut) {
		List<RoomDetails> totalRoomDetailsList = totalRoomDetialsByBranchId(branchId);
		Map<String, List<RoomDetails>> totalRoomDetailsMap = totalRoomDetailsMapByRoomType(totalRoomDetailsList);
		List<RoomDetails> bookedRoomDetails = bookedRoomDetailsByBranchIdAndCheckInAndCheckOut(branchId, checkIn,
				checkOut);
		Map<String, List<RoomDetails>> bookedRoomDetailsMap = bookedRoomDetailsMapByRoomType(bookedRoomDetails);
		Map<String, List<RoomDetails>> availableRoomDetailsMap = new HashMap<>();
		totalRoomDetailsMap.forEach((roomType, totalRooms) -> 
			availableRoomDetailsMap.put(roomType,
					getAvailableRooms(bookedRoomDetailsMap, totalRooms, totalRoomDetailsMap, roomType))
		);
		return roomTypeStatusMap(totalRoomDetailsMap,
				bookedRoomDetailsMap, availableRoomDetailsMap, checkIn);
	}

	public Map<String, Map<String, Number>> getStatusCountByCheckInDate(int branchId,
			List<RoomDetails> bookedRoomDetails) {
		List<RoomDetails> totalRoomDetailsList = totalRoomDetialsByBranchId(branchId);
		Map<String, List<RoomDetails>> totalRoomDetailsMap = totalRoomDetailsMapByRoomType(totalRoomDetailsList);
		Map<String, List<RoomDetails>> bookedRoomDetailsMap = bookedRoomDetailsMapByRoomType(bookedRoomDetails);
		Map<String, List<RoomDetails>> availableRoomDetailsMap = new HashMap<>();
		totalRoomDetailsMap.forEach((roomType, totalRooms) -> 
			availableRoomDetailsMap.put(roomType,
					getAvailableRooms(bookedRoomDetailsMap, totalRooms, totalRoomDetailsMap, roomType))
		);
		return roomsAvailabilityCountMapByRoomType(totalRoomDetailsMap, bookedRoomDetailsMap);
	}

	private Map<String, Map<String, Number>> roomTypeStatusMap(Map<String, List<RoomDetails>> totalRoomDetailsMap,
			Map<String, List<RoomDetails>> bookedRoomDetailsMap, Map<String, List<RoomDetails>> availableRoomDetailsMap,
			LocalDate checkIn) {

		Map<String, Map<String, Number>> roomTypeStatusMap = new HashMap<>();
		totalRoomDetailsMap.forEach((roomType, rooms) -> {
			Map<String, Number> availabilityCountMap = new HashMap<>();
			if (bookedRoomDetailsMap.get(roomType) != null) {
				int bookedRoomsCount = (bookedRoomDetailsMap.get(roomType)).size();
				int totalRoomsCount = (totalRoomDetailsMap.get(roomType)).size();
				int avilableRoomsCount = totalRoomsCount - bookedRoomsCount;
				availabilityCountMap.put(booked, bookedRoomsCount);
				availabilityCountMap.put(available, avilableRoomsCount);
			} else {
				int totalRoomsCount = (totalRoomDetailsMap.get(roomType)).size();
				availabilityCountMap.put(booked, 0);
				availabilityCountMap.put(available, totalRoomsCount);
			}
			List<RoomDetails> availableRoomDetails = availableRoomDetailsMap.get(roomType);
			RoomDetails firstAvailableRoom;
			if (!availableRoomDetails.isEmpty()) {
				firstAvailableRoom = availableRoomDetails.get(0);
				Optional<DynamicRent> dyanmicRent = dynamicRentRepository.findByRoomTypeAndBranchIdAndBookingDate(
						roomType, firstAvailableRoom.getRent().getBranch().getBranchid(), checkIn);
				if (dyanmicRent.isPresent()) {
					availabilityCountMap.put("Rent", dyanmicRent.get().getRent());

				} else {
					availabilityCountMap.put("Rent", firstAvailableRoom.getRent().getRoomRent());

				}
				availabilityCountMap.put("roomId", firstAvailableRoom.getRoomId());
				availabilityCountMap.put("roomNo", firstAvailableRoom.getRoomNo());
			} else {
				availabilityCountMap.put("Rent", 0);
				availabilityCountMap.put("roomId", 0);
				availabilityCountMap.put("roomNo", 0);
			}
			roomTypeStatusMap.put(roomType, availabilityCountMap);
		});
		return roomTypeStatusMap;
	}

	public Map<String, Map<String, Number>> roomsAvailabilityCountMapByRoomType(
			Map<String, List<RoomDetails>> totalRoomDetailsMap, Map<String, List<RoomDetails>> bookedRoomDetailsMap) {
		Map<String, Map<String, Number>> roomTypeStatusMap = new HashMap<>();
		totalRoomDetailsMap.forEach((roomType, rooms) -> {
			Map<String, Number> availabilityCountMap = new HashMap<String, Number>();
			if (bookedRoomDetailsMap.get(roomType) != null) {
				int bookedRoomsCount = (bookedRoomDetailsMap.get(roomType)).size();
				int totalRoomsCount = (totalRoomDetailsMap.get(roomType)).size();
				int avilableRoomsCount = totalRoomsCount - bookedRoomsCount;
				availabilityCountMap.put(booked, bookedRoomsCount);
				availabilityCountMap.put(available, avilableRoomsCount);
			} else {
				int totalRoomsCount = (totalRoomDetailsMap.get(roomType)).size();
				availabilityCountMap.put(booked, 0);
				availabilityCountMap.put(available, totalRoomsCount);
			}
			roomTypeStatusMap.put(roomType, availabilityCountMap);
		});
		return roomTypeStatusMap;
	}

	@Override
	public Map<String, List<RoomDetails>> findAvalaibleRooms(int branchId, LocalDate checkIn, LocalDate checkOut) {
		List<RoomDetails> totalRooms = roomDetailsRepository.findAll().stream()
				.filter(room -> room.getRent().getBranch().getBranchid() == branchId).collect(Collectors.toList());
		Booking[] book = restTemplate.getForObject(bookingUrl + checkIn + "/" + checkOut, Booking[].class);
		List<Booking> list = new ArrayList<>();
		list.addAll(Arrays.asList(book));
		List<RoomDetails> bookedRooms = list.stream()
				.filter(booking -> booking.getRoomDetails().getRent().getBranch().getBranchid() == branchId)
				.map(Booking::getRoomDetails).collect(Collectors.toList());
		return totalRooms.stream().filter(room -> 
			!bookedRooms.contains(room)
		).collect(Collectors.groupingBy(room -> room.getRent().getRoomType().getRoomType()));

	}

}