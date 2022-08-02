package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQueryService;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

	@Autowired
	private SaleQueryService saleQueryService;

	@GetMapping("/daily-sales")
	public List<DailySale> queryDailySales(DailySaleFilter filter) {
		return saleQueryService.queryDailySales(filter);
	}
}
