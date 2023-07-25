package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> queryDailySales(DailySaleFilter filter, String timeOffset);
}
