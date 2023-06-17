package com.feuji.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.feuji.dto.Booking;

public interface BookingService {
	
	public void saveBooking(Booking booking);

	public List<Booking> getBookingDetails();

	public Booking getBooking(int id);
	
	public Optional<List<Booking>> getAllByQuery(LocalDate checkin);
	
	public Optional<List<Booking>> getAllByQuery(LocalDate checkin, LocalDate checkOut);
	
	public Optional<List<Booking>> getAllByQuery(LocalDate checkin,LocalDate checkOut,int branchId) ;
}
