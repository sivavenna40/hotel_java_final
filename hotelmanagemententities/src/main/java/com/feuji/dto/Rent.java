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

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "rent")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rentId")

public class Rent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rent_id")
	private int rentId;

	@Column(name = "rent")
	private double roomRent;

	@ManyToOne(cascade =  { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "room_type_id")
	private RoomType roomType;

	@JsonBackReference
	@ManyToOne(cascade =  CascadeType.ALL)
	@JoinColumn(name = "branch_id")
	private Branch branch;

	@JsonIgnore
	@OneToMany(mappedBy = "rent",  cascade =CascadeType.ALL)
	private List<RoomDetails> roomDetails;

	@Override
	public String toString() {
		return "Rent [rentId=" + rentId + ", roomRent=" + roomRent + ", roomType=" + roomType + ", branch=" + branch
				+ "]";
	}

}
