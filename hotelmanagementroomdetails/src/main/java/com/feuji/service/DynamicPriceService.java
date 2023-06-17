package com.feuji.service;

import java.util.List;

import com.feuji.dto.DynamicPrice;

public interface DynamicPriceService {

	void findRateByUpperBoundAndLowerBound();

	List<DynamicPrice> findAllRates();

}