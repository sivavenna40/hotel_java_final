package com.feuji.service;

import java.util.List;

import com.feuji.dto.AdminLogin;
import com.feuji.dto.Branch;



public interface AdminLoginService {
	
	public List<Branch> verifyAdmin(String username,String password);
	public void saveadminLogin(AdminLogin adminLogin);
}
