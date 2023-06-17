package com.feuji.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private int hotelId;

	@Column(name = "hotel_name")
	private String hotelName;

	@Column(name = "city")
	private String city;

	@JsonIgnore
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<Branch> branches;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<AdminLogin> adminLogins;

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", city=" + city + "]";
	}
	
	
}
