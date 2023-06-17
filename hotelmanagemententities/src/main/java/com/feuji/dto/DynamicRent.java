package com.feuji.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dynamic_rent")
public class DynamicRent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dynamic_rent_id")
	private int id;

	@Column(name = "room_type")
	private String roomType;

	@Column(name = "booking_date")
	private LocalDate bookingDate;

	@Column(name = "rent")
	private double rent;

	@Column(name = "branch_id")
	private int branchId;

	@Override
	public String toString() {
		return "DynamicRent [id=" + id + ", roomType=" + roomType + ", bookingDate=" + bookingDate + ", rent=" + rent
				+ ", branchId=" + branchId + "]";
	}

}
