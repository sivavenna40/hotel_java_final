package com.feuji.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.RoomDetails;

public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Integer> {
	
}
