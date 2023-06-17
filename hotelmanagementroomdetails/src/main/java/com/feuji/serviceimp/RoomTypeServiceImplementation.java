package com.feuji.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.RoomTypeRepository;
import com.feuji.dto.RoomType;
import com.feuji.service.RoomTypeService;

@Service
public class RoomTypeServiceImplementation implements RoomTypeService{
	
	@Autowired
	RoomTypeRepository roomTypeRepository;

	@Override
	public void saveRoomType(RoomType roomType) {
		roomTypeRepository.save(roomType);
	}

	@Override
	public RoomType getRoomType(int id) {
		Optional<RoomType> optionalRoomType = roomTypeRepository.findById(id);
		RoomType roomType=null;
		if(optionalRoomType.isPresent()) {
			roomType=optionalRoomType.get();
		}
		return roomType;
	}

	@Override
	public List<RoomType> getRoomTypes() {
		
		return roomTypeRepository.findAll();
	}

	@Override
	public void deleteRoomType(int id) {
		roomTypeRepository.deleteById(id);
	}

	@Override
	public void deleteRoomTypeById(int id) {
		roomTypeRepository.deleteById(id);
	}

	@Override
	public RoomType findByRoomType(String roomType) {
		Optional<RoomType> optionalRoomType = roomTypeRepository.findByRoomType(roomType);
		RoomType roomTypee=null;
		if(optionalRoomType.isPresent()) {
			roomTypee=optionalRoomType.get();
		}
		return roomTypee;
	}

}
