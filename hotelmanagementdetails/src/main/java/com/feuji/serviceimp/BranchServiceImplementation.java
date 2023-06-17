package com.feuji.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.BranchRepository;
import com.feuji.dto.Branch;
import com.feuji.dto.Hotel;
import com.feuji.service.BranchService;
import com.feuji.service.HotelService;

@Service
public class BranchServiceImplementation implements BranchService {

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	HotelService hotelService;

	@Override
	public void saveBranch(Branch branch) {
		branchRepository.save(branch);
	}

	@Override
	public List<Branch> getBranchDetails() {
		return branchRepository.findAll();
	}

	@Override
	public Optional<Branch> getBranchById(int id) {
		return branchRepository.findById(id);
	}

	@Override
	public void deleteBranch(int id) {
		branchRepository.deleteById(id);
	}

	public Branch saveBranch(Branch branch, int id) {
		Hotel hotel = hotelService.getHotelById(id);
		if (branchRepository
				.findByBranchnameAndBranchLocationAndHotel(branch.getBranchname(), branch.getBranchLocation(), hotel)
				.isEmpty() || !branchRepository.findById(branch.getBranchid()).isEmpty()) {
			branch.setHotel(hotel);
			saveBranch(branch);
			return branch;
		}
		saveBranch(branch);

		return branch;
	}

}
