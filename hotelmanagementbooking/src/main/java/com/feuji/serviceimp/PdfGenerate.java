package com.feuji.serviceimp;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dto.Booking;
import com.feuji.dto.Customer;
import com.feuji.service.BookingService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfGenerate {

	@Autowired
	BookingService bookingService;

	public void generate(HttpServletResponse response, int bookId) throws DocumentException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=Mypdf.pdf");
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Rectangle rect = new Rectangle(577, 825, 18, 15); // you can resize rectangle
		rect.enableBorderSide(1);
		rect.enableBorderSide(2);
		rect.enableBorderSide(4);
		rect.enableBorderSide(8);
		rect.setBorderColor(BaseColor.BLACK);
		rect.setBorderWidth(1);
		document.add(rect);

		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		Paragraph paragraph = new Paragraph("Trip Confirmation Voucher ", fontTiltle);
		paragraph.setAlignment(Element.ALIGN_LEFT);

		Font data = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		data.setSize(10);
		document.add(new Paragraph("Hotel Booking ID - " + bookId, data));

		Booking booking = bookingService.getBooking(bookId);
		document.add(new Paragraph(
				".............................................................................................................................."));
		document.add(new Paragraph("Dear customer"));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph(" Your Booking is confirmed."));

		document.add(new Paragraph(" Thank you for using hotel.com for booking your vacation"));
		document.add(new Paragraph(" For your reference, your hotel Booking ID is :- " + bookId));
		document.add(new Paragraph(" The customer Id is :- " + booking.getCustomer().getCustomerId()));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph(
				"If your hotel booking includes a complimentary data, you will need to call the hotel directly to let them know your room details."));
		document.add(new Paragraph(
				"You will need to carry a printout of this e-mail and present it at the hotel at the time of check-in."));
		document.add(Chunk.NEWLINE);

		document.add(new Paragraph(
				"Please note that you will receive the hotel Service fee invoice for your booking on the day of checkout on the email ID using which the booking will be made."));
		document.add(Chunk.NEWLINE);

		document.add(new Paragraph("We hope you have a pleasant stay and look forward to assisting you again!"));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Team hotelmanagement.com"));

		document.add(Chunk.NEWLINE);

		Font description = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		description.setSize(10);

		Paragraph paragraph1 = new Paragraph(
				"THIS IS YOUR HOTEL CONFIRMATION VOUCHER. A PRINTED COPY OF THIS MUST BE PRESENTED AT THE HOTEL AT THE TIME OF CHECK-IN.",
				description);
		paragraph1.setAlignment(Element.ALIGN_CENTER);

		document.add(paragraph1);

		document.add(Chunk.NEWLINE);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(5);

		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 2, 2, 2, 2, 2 });
		table.setSpacingBefore(3);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setPadding(4);

		cell.setPhrase(new Phrase("Name"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gender"));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Phone No."));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Aadhar No."));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Total Members"));
		table.addCell(cell);

		Customer customer = booking.getCustomer();

		table.addCell(customer.getCustomerName());
		table.addCell(customer.getCustomerGender());
		table.addCell(String.valueOf(customer.getCustomerPhoneNo()));
		table.addCell(String.valueOf(customer.getCustomerAadharNo()));
		table.addCell(String.valueOf(customer.getTotalMembers()));

		document.add(table);

		document.close();
	}

}