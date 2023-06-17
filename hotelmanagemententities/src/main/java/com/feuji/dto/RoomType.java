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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_type")
public class RoomType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_type_id")
	private int roomTypeId;

	@Column(name = "room_type")
	private String roomType;
	
	@JsonIgnore
	@OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
	private List<Rent> rents;

	@Override
	public String toString() {
		return "RoomType [roomTypeId=" + roomTypeId + ", roomType=" + roomType + "]";
	}
	
	

}
