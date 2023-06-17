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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "branchid")

public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id")
	private int branchid;

	@Column(name = "branch_name")
	private String branchname;

	@Column(name = "branch_location")
	private String branchLocation;
	
	@ManyToOne(cascade =  { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@JsonManagedReference
	@OneToMany(mappedBy = "branch", cascade =  CascadeType.ALL)
	private List<Rent> rents;
	
	@JsonIgnore
	@OneToOne(mappedBy = "branch",cascade = {CascadeType.MERGE,CascadeType.MERGE})
	private BranchLogin branchLogin;
	
	@Override
	public String toString() {
		return "Branch [branchid=" + branchid + ", branchname=" + branchname + ", branchLocation=" + branchLocation
				+ ", hotel=" + hotel + "]";
	}

	
	
	

}
