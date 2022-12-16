package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estatísticas")
public interface StatisticsControllerOpenApi {

	@ApiOperation("Consultar")
	List<DailySale> queryDailySales(DailySaleFilter filter, String timeOffset);

	@ApiOperation("Emitir")
	ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter, String timeOffset);

}