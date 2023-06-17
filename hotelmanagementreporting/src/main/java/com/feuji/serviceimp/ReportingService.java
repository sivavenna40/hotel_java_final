package com.feuji.serviceimp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.controller.AlterPort;
import com.feuji.dto.Booking;

@Service
public class ReportingService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private AlterPort alterPort;
	@Value("${rest.bookingurl}")
	private String bookingUrl;

	public Map<LocalDate, Long> dailyReport() {

		Booking[] bookings = restTemplate.getForObject(alterPort.getUrl(bookingUrl), Booking[].class);
		List<Booking> booking = new ArrayList<>();
		booking.addAll(Arrays.asList(bookings));
		return booking.stream().collect(Collectors.groupingBy(Booking::getCheckInTime, Collectors.counting()));
	}

	public Map<Object, Long> monthlyReport() {
		Booking[] bookings = restTemplate.getForObject(alterPort.getUrl(bookingUrl), Booking[].class);
		List<Booking> booking = new ArrayList<>();
		booking.addAll(Arrays.asList(bookings));
		return booking.stream()
				.collect(Collectors.groupingBy(n -> n.getCheckInTime().getMonth(), Collectors.counting()));

	}

	public Map<Object, Long> yearlyReport() {
		Booking[] bookings = restTemplate.getForObject(alterPort.getUrl(bookingUrl), Booking[].class);
		List<Booking> booking = new ArrayList<>();
		booking.addAll(Arrays.asList(bookings));
		return booking.stream()
				.collect(Collectors.groupingBy(n -> n.getCheckInTime().getYear(), Collectors.counting()));
	}

	public Map<Object, Double> monthlyTotalAmountReport() {

		Booking[] bookings = restTemplate.getForObject(alterPort.getUrl(bookingUrl), Booking[].class);
		List<Booking> booking = new ArrayList<>();
		booking.addAll(Arrays.asList(bookings));
		return booking.stream().collect(Collectors.groupingBy(n -> n.getCheckInTime().getMonth(),
				Collectors.summingDouble(Booking::getTotalAmount)));
	}

	public Map<Object, Double> yearlyTotalAmountReport() {
		Booking[] bookings = restTemplate.getForObject(alterPort.getUrl(bookingUrl), Booking[].class);
		List<Booking> booking = new ArrayList<>();
		booking.addAll(Arrays.asList(bookings));
		return booking.stream().collect(Collectors.groupingBy(n -> n.getCheckInTime().getYear(),
				Collectors.summingDouble(Booking::getTotalAmount)));
	}

	public Map<Object, Double> dailyTotalAmountReport() {
		Booking[] bookings = restTemplate.getForObject(alterPort.getUrl(bookingUrl), Booking[].class);
		List<Booking> booking = new ArrayList<>();
		booking.addAll(Arrays.asList(bookings));
		return booking.stream().collect(
				Collectors.groupingBy(Booking::getCheckInTime, Collectors.summingDouble(Booking::getTotalAmount)));
	}

}