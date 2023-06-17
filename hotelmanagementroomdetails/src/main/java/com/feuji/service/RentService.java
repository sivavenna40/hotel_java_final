package com.feuji.service;

import java.util.List;
import java.util.Optional;

import com.feuji.dto.Rent;

public interface RentService {
	
	public void saveRent(Rent rent);

	public List<Rent> getRents();

	public Rent getRentById(int id);
	
	public void deleteRentById(int id);
	
	Optional<Rent> findByBranchAndRoomType(int branchId,String roomType);
}
