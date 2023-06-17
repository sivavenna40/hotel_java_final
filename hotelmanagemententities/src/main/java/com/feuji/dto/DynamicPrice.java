package com.feuji.dto;

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
@Table(name = "dynamic_price")
public class DynamicPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dp_id")
	private int id;

	@Column(name = "upper_bound")
	private int upperBound;

	@Column(name = "lower_bound")
	private int lowerBound;

	@Column(name = "rent_change")
	private double price;

	@Override
	public String toString() {
		return "DynamicPrice [id=" + id + ", upperBound=" + upperBound + ", lowerBound=" + lowerBound + ", price="
				+ price + "]";
	}

}
