package com.algaworks.algafood.infrastructure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQueryService;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {

	@Override
	public List<DailySale> queryDailySales(DailySaleFilter filter) {
		return null;
	}
}
