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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branch_login")
public class BranchLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_id")
	public int id;

	@Column(name="username")
	public String username;

	@Column(name="password")
	public String password;

	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
	@JoinColumn(name = "branch_id")
	public Branch branch;
}
