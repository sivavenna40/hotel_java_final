package com.feuji.service;

import java.util.List;

import com.feuji.dto.RoomType;

public interface RoomTypeService {
	
	void saveRoomType(RoomType roomType);
	
	RoomType getRoomType(int id);
	
	List<RoomType> getRoomTypes();
	
	void deleteRoomType(int id);

	void deleteRoomTypeById(int id);
	
	public RoomType findByRoomType(String roomType);
	
	
}
