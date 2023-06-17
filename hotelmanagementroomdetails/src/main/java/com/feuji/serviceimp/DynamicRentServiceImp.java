package com.feuji.serviceimp;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.controller.AlterPort;
import com.feuji.dao.DynamicRentRepository;
import com.feuji.dto.Booking;
import com.feuji.dto.Branch;
import com.feuji.dto.DynamicPrice;
import com.feuji.dto.DynamicRent;
import com.feuji.dto.Rent;
import com.feuji.dto.RoomDetails;
import com.feuji.service.DynamicPriceService;
import com.feuji.service.DynamicRentService;
import com.feuji.service.RoomDetailsService;

@Service
public class DynamicRentServiceImp implements DynamicRentService {

	@Autowired
	DynamicRentRepository dynamicRentRepository;

	@Autowired
	RoomDetailsService roomDetailsService;

	@Autowired
	DynamicPriceService dynamicPriceService;

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private AlterPort alterPort;
	@Value("${rest.booking}")
	String bookingUrl;

	@Value("${rest.getallbranchurl}")
	String getAllBranchUrl;

	@Override
	@Scheduled(fixedRate = 3000000)
//	@Scheduled(cron = "1 13 18 * * ?")
	public void scheduledMethod() {
		saveDynamicRents();

	}

	@Override
	public void saveDynamicRents() {
		Branch[] branchArray = restTemplate.getForObject(alterPort.getUrl(getAllBranchUrl), Branch[].class);
		List<Branch> branches = Arrays.asList(branchArray);
		List<DynamicPrice> rates = dynamicPriceService.findAllRates();
		branches.forEach(branch -> {
			Booking[] bookingArray = restTemplate.getForObject(alterPort.getUrl(bookingUrl) + LocalDate.now(), Booking[].class);
			List<Booking> currentBookings = Arrays.asList(bookingArray);
			List<RoomDetails> roomDetailsList = this.bookedRoomDetails(currentBookings, branch.getBranchid());
			Map<String, Map<String, Number>> detailsMap = roomDetailsService
					.getStatusCountByCheckInDate(branch.getBranchid(), roomDetailsList);
			branch.getRents().forEach(rent -> {
				String roomType = rent.getRoomType().getRoomType();
				double bookedPercentage = findBookedPercentage(detailsMap, roomType);
				DynamicRent dynamicRent = buildDynamicRent(branch, roomType, rates, bookedPercentage, rent);
				saveDynamicRent(roomType, dynamicRent);
			});
		});
	}

	private double findBookedPercentage(Map<String, Map<String, Number>> detailsMap, String roomType) {
		double bookedCount = 0;
		double availableCount = 0;
		if (detailsMap.get(roomType).get("Booked") != null) {
			bookedCount = (int) detailsMap.get(roomType).get("Booked");
		}
		if (detailsMap.get(roomType).get("Available") != null) {
			availableCount = (int) detailsMap.get(roomType).get("Available");
		}
		double bookedPercentage = 0;
		if (availableCount + bookedCount != 0) {
			bookedPercentage = bookedCount / (availableCount + bookedCount) * 100;
		}
		return bookedPercentage;
	}

	private List<RoomDetails> bookedRoomDetails(List<Booking> bookings, int branchId) {
		List<Integer> roomIdList = bookings.stream().map(book -> book.getRoomDetails().getRoomId())
				.collect(Collectors.toList());
		return roomIdList.stream().map(roomId -> roomDetailsService.findRoomDetails(roomId))
				.filter(room -> room.getRent().getBranch().getBranchid() == branchId).collect(Collectors.toList());
	}

	private void saveDynamicRent(String roomType, DynamicRent dynamicRent) {
		Optional<DynamicRent> optionalDynamicRent = dynamicRentRepository.findByRoomTypeAndBranchIdAndBookingDate(
				roomType, dynamicRent.getBranchId(), dynamicRent.getBookingDate());
		if (optionalDynamicRent.isPresent()) {
			dynamicRent.setId(optionalDynamicRent.get().getId());
		}
		dynamicRentRepository.save(dynamicRent);
	}

	private DynamicRent buildDynamicRent(Branch branch, String roomType, List<DynamicPrice> rates,
			double bookedPercentage, Rent rent) {
		DynamicRent dynamicRent = new DynamicRent();
		dynamicRent.setBookingDate(LocalDate.now());
		dynamicRent.setBranchId(branch.getBranchid());
		dynamicRent.setRoomType(roomType);
		rates.forEach(rate -> {
			if (rate.getLowerBound() <= bookedPercentage && rate.getUpperBound() >= bookedPercentage) {
				dynamicRent.setRent(rent.getRoomRent() * rate.getPrice());
			}
		});
		return dynamicRent;
	}

}
