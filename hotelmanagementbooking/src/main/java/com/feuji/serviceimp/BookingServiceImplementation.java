package com.feuji.serviceimp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.BookingRepository;
import com.feuji.dto.Booking;
import com.feuji.dto.RoomDetails;
import com.feuji.service.BookingService;

@Service
public class BookingServiceImplementation implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Override
	public void saveBooking(Booking booking) {
		booking.getCustomer().setBooking(booking);
		booking.setRent(booking.getRoomDetails().getRent().getRoomRent());
		int numberOfDays = (int) ChronoUnit.DAYS.between(booking.getCheckInTime(), booking.getCheckOutTime());
		long totalAmount = (long) (booking.getRent() * numberOfDays);
		booking.setDueAmount(totalAmount - booking.getAdvanceAmount());
		if (booking.getBookingId() != 0) {
			RoomDetails roomDetails = this.getBooking(booking.getBookingId()).getRoomDetails();
			booking.setRoomDetails(roomDetails);
		}
		booking.setDurationOfStay(numberOfDays);
		booking.setTotalAmount(totalAmount);
		bookingRepository.save(booking);
	}

	@Override
	public List<Booking> getBookingDetails() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking getBooking(int id) {
		Optional<Booking> optionlBooking= bookingRepository.findById(id);
		Booking booking = new Booking();
		if(optionlBooking.isPresent())
		{
			booking=optionlBooking.get();
		}
		return booking;
	}
	public Optional<List<Booking>> getAllByQuery(LocalDate checkin, LocalDate checkOut) {
		return  bookingRepository.findByCheckInTime(checkin);
	}
	
	public Optional<List<Booking>> getAllByQuery(LocalDate checkin, LocalDate checkOut,int branchId) {
		Optional<List<Booking>> optionalBookings = bookingRepository.findByCheckOutTimeAndCheckInTime(checkin,checkOut);
		List<Booking> bookings=Collections.emptyList();
		if(optionalBookings.isPresent()) {
			 bookings = optionalBookings.get().stream().filter(book->book.getRoomDetails().getRent().getBranch().getBranchid()==branchId).collect(Collectors.toList());
		}
		return Optional.of(bookings);
	}

	public Optional<List<Booking>> getAllByQuery(LocalDate checkin) {
		return bookingRepository.findByCheckInTime(checkin);

	}

}
