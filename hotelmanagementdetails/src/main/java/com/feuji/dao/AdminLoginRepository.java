package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.AdminLogin;



public interface AdminLoginRepository extends JpaRepository<AdminLogin, Integer> {
	
	Optional<AdminLogin> findByUsernameAndPassword(String username, String password);

}
