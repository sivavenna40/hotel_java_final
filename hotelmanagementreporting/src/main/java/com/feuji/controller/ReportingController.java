package com.feuji.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.serviceimp.ReportingService;

@RestController
@RequestMapping("/reporting")
public class ReportingController {

	@Autowired
	ReportingService reportingService;

	@GetMapping("/daily")
	public Map<LocalDate, Long> dailyReport() {
		return reportingService.dailyReport();
	}

	@GetMapping("/monthly")
	public Map<Object, Long> monthlyReport() {
		return reportingService.monthlyReport();
	}

	@GetMapping("/yearly")
	public Map<Object, Long> yearlyReport() {
		return reportingService.yearlyReport();
	}

	@GetMapping("/dailyamount")
	public Map<Object, Double> dailyTotalAmountReport() {
		return reportingService.dailyTotalAmountReport();
	}

	@GetMapping("/monthlyamount")
	public Map<Object, Double> monthlyTotalAmountReport() {
		return reportingService.monthlyTotalAmountReport();
	}

	@GetMapping("/yearlyamount")
	public Map<Object, Double> yearlyTotalAmountReport() {
		return reportingService.yearlyTotalAmountReport();
	}

	@GetMapping("/map")
	public Map<String, String> getMapData() {
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		// Add more data to the map
		return map;
	}


}
