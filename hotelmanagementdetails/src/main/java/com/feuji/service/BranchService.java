package com.feuji.service;

import java.util.List;
import java.util.Optional;

import com.feuji.dto.Branch;

public interface BranchService {

	public void saveBranch(Branch branch);

	public List<Branch> getBranchDetails();

	public Optional<Branch> getBranchById(int id);

	public void deleteBranch(int id);

	public Branch saveBranch(Branch branch, int id);

}
