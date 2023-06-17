package com.feuji.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.dto.DynamicPrice;
import com.feuji.service.DynamicPriceService;


@RestController
@RequestMapping("/dynamicprice")
public class DynamicPriceController {
	
	@Autowired
	private DynamicPriceService dynamicPriceService;

	@GetMapping(value="/getall",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DynamicPrice>> getBranches(){
		List<DynamicPrice> getAll = dynamicPriceService.findAllRates();
		return ResponseEntity.ok().body(getAll);
	}
}