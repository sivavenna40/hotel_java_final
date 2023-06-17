package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.Branch;
import com.feuji.dto.Rent;
import com.feuji.dto.RoomType;

public interface RentRepository extends JpaRepository<Rent, Integer>{
	
	Optional<Rent> findByBranchAndRoomType(Branch branch,RoomType roomType);
}
