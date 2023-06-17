package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.BranchLogin;



public interface BranchLoginRepository extends JpaRepository<BranchLogin, Integer> {

	Optional<BranchLogin> findByUsernameAndPassword(String username, String password);
}
