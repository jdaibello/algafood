package com.algaworks.algafood.infrastructure.service.report;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.service.SaleReportService;

@Service
public class PdfSaleReportService implements SaleReportService {

	@Override
	public byte[] issueDailySales(DailySaleFilter filter, String timeOffset) {
		return null;
	}
}
