package com.feuji.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.dao.DynamicPriceRepository;
import com.feuji.dto.DynamicPrice;
import com.feuji.service.DynamicPriceService;

@Service
public class DynamicPriceServiceImplementation implements DynamicPriceService {

	@Autowired
	DynamicPriceRepository dynamicPriceRepository;

	@Override
	public void findRateByUpperBoundAndLowerBound() {
		dynamicPriceRepository.findByUpperBoundLowerBound(20, 0);
	}
	
	@Override
	public List<DynamicPrice> findAllRates() {
		return dynamicPriceRepository.findAll();
	}
}
