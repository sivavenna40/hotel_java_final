package com.feuji.service;

import java.util.List;

import com.feuji.dto.Branch;
import com.feuji.dto.BranchLogin;

public interface BranchLoginService {

	public void saveBranchLogin(BranchLogin branchLogin);

	public List<BranchLogin> getBranchLoginDetails();

	public BranchLogin getBranchLoginById(int id);

	public void deleteBranchLogin(int id);
	
	public Branch verifyBranch(String username,String password);
}
