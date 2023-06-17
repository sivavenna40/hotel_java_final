package com.feuji.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.BranchLoginRepository;
import com.feuji.dto.Branch;
import com.feuji.dto.BranchLogin;
import com.feuji.service.BranchLoginService;
import com.feuji.service.BranchService;

@Service
public class BranchLoginServiceImplementation implements BranchLoginService {

	@Autowired
	BranchLoginRepository branchLoginRepository;

	@Autowired
	BranchService branchService;

	@Override
	public void saveBranchLogin(BranchLogin branchLogin) {
		branchLoginRepository.save(branchLogin);
	}

	@Override
	public List<BranchLogin> getBranchLoginDetails() {
		return branchLoginRepository.findAll();
	}

	@Override
	public BranchLogin getBranchLoginById(int id) {
		BranchLogin branchLogin=null;
		Optional<BranchLogin> optionalBranchLogin = branchLoginRepository.findById(id);
		if(optionalBranchLogin.isPresent()) {
			branchLogin=optionalBranchLogin.get();
		}
		return branchLogin;
	}

	@Override
	public void deleteBranchLogin(int id) {
		branchLoginRepository.deleteById(id);
	}

	@Override
	public Branch verifyBranch(String username, String password) {
		Optional<BranchLogin> branchLogin = branchLoginRepository.findByUsernameAndPassword(username, password);
		if (branchLogin.isPresent()) {
			 Optional<Branch> optionalBranch = branchService.getBranchById(branchLogin.get().getBranch().getBranchid());
			 if(optionalBranch.isPresent()) {
				 return optionalBranch.get();
			 }
			 return null;
		} else
			return null;
	}
}
