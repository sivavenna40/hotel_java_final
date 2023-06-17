package com.feuji.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.dto.Booking;
import com.feuji.dto.EmailDetails;
import com.feuji.service.BookingService;
import com.feuji.serviceimp.PdfGenerate;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private PdfGenerate generate;

	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EmailController emailController;

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> save(@RequestBody Booking booking) {
		bookingService.saveBooking(booking);
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Booking> getBranch(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(bookingService.getBooking(id));
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> getBranches() {
		List<Booking> getAll = bookingService.getBookingDetails();
		return ResponseEntity.ok().body(getAll);
	}

	@GetMapping("/pdf/customer/{bookId}")
	public void generatePdfForUser(@PathVariable(value = "bookId") int bookId) throws DocumentException, IOException {

		response.setContentType("application/pdf");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);

		generate.generate(response, bookId);
		String attachment = "C:/Users/dell/Downloads/booking_" + bookId + ".pdf";
		String email = bookingService.getBooking(bookId).getCustomer().getCustomerEmail();
		String subject = "Room Confirmation";
		String body = "Dear  " + bookingService.getBooking(bookId).getCustomer().getCustomerName()
				+ ", Room successfully Booked with   " + bookingService.getBooking(bookId).getRoomDetails().getRoomNo();
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setAttachment(attachment);
		emailDetails.setToEmail(email);
		emailDetails.setBody(body);
		emailDetails.setSubject(subject);
		try {
			emailController.sendEmailWithAttachment(emailDetails);
		} catch (MessagingException e) {

			e.printStackTrace();
		}

	}

	@GetMapping(value = "/check/{checkin}/{checkout}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Booking> getAllByQuery(@PathVariable("checkin") String checkin,
			@PathVariable("checkout") String checkOut) {
		LocalDate d1 = LocalDate.parse(checkin);
		LocalDate d2 = LocalDate.parse(checkOut);
		Optional<List<Booking>> booking = bookingService.getAllByQuery(d1, d2);
		if (booking.isPresent()) {
			return booking.get();
		} else {
			return Collections.emptyList();
		}

	}

	@GetMapping("/{branchId}/{checkIn}/{checkOut}")
	public ResponseEntity<List<Booking>> getAllByQuery(@PathVariable String checkIn, @PathVariable String checkOut,
			@PathVariable int branchId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // specify the pattern of the string
		checkIn = checkIn.replace("\"", "");
		checkOut = checkOut.replace("\"", "");

		LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
		LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
		Optional<List<Booking>> booking = bookingService.getAllByQuery(checkInDate, checkOutDate, branchId);
		if (booking.isPresent()) {
			return ResponseEntity.ok().body(booking.get());
		}
		return ResponseEntity.ok().body(Collections.emptyList());
	}

	@GetMapping(value = "/checkin/{checkin}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> getAllByQuery(@PathVariable("checkin") String checkin) {
		LocalDate d1 = LocalDate.parse(checkin);
		Optional<List<Booking>> booking = bookingService.getAllByQuery(d1);
		if (booking.isPresent()) {
			return ResponseEntity.ok().body(booking.get());
		}
		return ResponseEntity.ok().body(Collections.emptyList());

	}

}
