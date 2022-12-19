package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.StatisticsControllerOpenApi;
import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQueryService;
import com.algaworks.algafood.domain.service.SaleReportService;

@RestController
@RequestMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController implements StatisticsControllerOpenApi {

	@Autowired
	private SaleQueryService saleQueryService;

	@Autowired
	private SaleReportService saleReportService;

	@Override
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DailySale> queryDailySales(DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return saleQueryService.queryDailySales(filter, timeOffset);
	}

	@Override
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		byte[] bytesPdf = saleReportService.issueDailySales(filter, timeOffset);
		var headers = new HttpHeaders();

		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
	}
}
