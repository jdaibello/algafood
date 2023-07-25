package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.service.SaleQueryService;
import com.algaworks.algafood.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfSaleReportService implements SaleReportService {

    @Autowired
    private SaleQueryService saleQueryService;

    @Override
    public byte[] issueDailySales(DailySaleFilter filter, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
            var parameters = new HashMap<String, Object>();
            var dailySales = saleQueryService.queryDailySales(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(dailySales);

            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir o relatório de vendas diárias", e);
        }
    }
}
