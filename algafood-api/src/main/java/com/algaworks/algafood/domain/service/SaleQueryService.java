package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;

public interface SaleQueryService {

	List<DailySale> queryDailySales(DailySaleFilter filter, String timeOffset);
}
