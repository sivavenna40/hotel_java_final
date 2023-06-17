package com.feuji.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int bookingId;

	@Column(name = "checkin_time")
	private LocalDate checkInTime;

	@Column(name = "checkout_time")
	private LocalDate checkOutTime;

	@Column(name = "total_amount")
	private double totalAmount;

	@Column(name = "advance_amount")
	private double advanceAmount;

	@Column(name = "due_amount")
	private double dueAmount;
	
	@Column(name = "duration_Of_Stay")
	private int durationOfStay;
	
	@Column(name="rent")
	private double rent;
	
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "room_id")
	private RoomDetails roomDetails;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "booking",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Customer customer;

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", checkInTime=" + checkInTime + ", checkOutTime=" + checkOutTime
				+ ", totalAmount=" + totalAmount + ", advanceAmount=" + advanceAmount + ", dueAmount=" + dueAmount+",rent="+rent
				+ ", roomDetails=" + roomDetails + ", customer=" + customer + "]";
	}

}
