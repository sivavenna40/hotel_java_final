package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.Branch;
import com.feuji.dto.Hotel;

public interface BranchRepository extends JpaRepository<Branch, Integer>{

	Optional<Branch> findByBranchnameAndBranchLocationAndHotel(String branchname,String branchLocation,Hotel hotel);
}

