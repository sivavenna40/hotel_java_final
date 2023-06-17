package com.feuji.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_gender")
	private String customerGender;
    @Column(name="customer_email")
	private String customerEmail;
	@Column(name = "customer_phone_no")
	private long customerPhoneNo;

	@Column(name = "customer_locality")
	private String customerLocality;

	@Column(name = "customer_state")
	private String customerState;

	@Column(name = "customer_country")
	private String customerCountry;

	@Column(name = "customer_aadhar_no")
	private long customerAadharNo;

	@Column(name = "total_members")
	private int totalMembers;

	@Column(name = "purpose")
	private String purpose;
	
	@JsonBackReference
	@OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerGender="
				+ customerGender + ", customerPhoneNo=" + customerPhoneNo + ", customerLocality=" + customerLocality
				+ ", customerState=" + customerState + ", customerCountry=" + customerCountry + ", customerAadharNo="
				+ customerAadharNo + ", totalMembers=" + totalMembers + ", purpose=" + purpose + "]";
	}
	
	
}
