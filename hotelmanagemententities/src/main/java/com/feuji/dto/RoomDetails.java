package com.feuji.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_details")
public class RoomDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int roomId;

	@Column(name = "room_no")
	private int roomNo;



	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "rent_id")
	private Rent rent;

	@JsonIgnore
	@OneToMany(mappedBy = "roomDetails", cascade = CascadeType.ALL)
	private List<Booking> bookings;

	@Override
	public String toString() {
		return "RoomDetails [roomId=" + roomId + ", roomNo=" + roomNo + ", rent=" + rent + "]";
	}

}
