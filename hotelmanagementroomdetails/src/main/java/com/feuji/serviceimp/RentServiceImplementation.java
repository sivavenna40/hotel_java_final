package com.feuji.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.dao.RentRepository;
import com.feuji.dto.Branch;
import com.feuji.dto.Rent;
import com.feuji.dto.RoomType;
import com.feuji.service.RentService;

@Service
public class RentServiceImplementation implements RentService {

	@Autowired
	RentRepository rentRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private RoomTypeServiceImplementation roomTypeServiceImplementation;

	@Value("{rest.branchurl}")
	String branchUrl;

	@Override
	public void saveRent(Rent rent) {
		rentRepository.save(rent);
	}

	@Override
	public Rent getRentById(int id) {
		Optional<Rent> optionalRent = rentRepository.findById(id);
		Rent rent = null;
		if (optionalRent.isPresent()) {
			rent = optionalRent.get();
		}
		return rent;
	}

	@Override
	public List<Rent> getRents() {
		return rentRepository.findAll();
	}

	@Override
	public void deleteRentById(int id) {
		rentRepository.deleteById(id);
	}

	@Override
	public Optional<Rent> findByBranchAndRoomType(int branchId, String roomType) {
		Branch branch = restTemplate.getForObject(branchUrl + branchId, Branch.class);
		RoomType roomType1 = roomTypeServiceImplementation.findByRoomType(roomType);
		return rentRepository.findByBranchAndRoomType(branch, roomType1);
	}

}
