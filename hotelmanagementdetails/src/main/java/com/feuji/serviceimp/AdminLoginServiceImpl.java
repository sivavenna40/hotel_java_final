package com.feuji.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.AdminLoginRepository;
import com.feuji.dto.AdminLogin;
import com.feuji.dto.Branch;
import com.feuji.service.AdminLoginService;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	private AdminLoginRepository adminLoginRepository;

	@Override
	public List<Branch> verifyAdmin(String username, String password) {

		Optional<AdminLogin> adminLogin = adminLoginRepository.findByUsernameAndPassword(username, password);
		List<Branch> branches=null;
		if (adminLogin.isPresent()) {
			branches= adminLogin.get().getHotel().getBranches();
		} 
		return branches;
	}

	public void saveadminLogin(AdminLogin adminLogin) {
		adminLoginRepository.save(adminLogin);
	}
}
