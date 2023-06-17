package com.feuji.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.dto.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer>{
	
	Optional<RoomType> findByRoomType(String roomType);
}
