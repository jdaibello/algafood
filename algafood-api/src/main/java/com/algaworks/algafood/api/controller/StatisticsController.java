package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.openapi.controller.StatisticsControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQueryService;
import com.algaworks.algafood.domain.service.SaleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController implements StatisticsControllerOpenApi {

    public static class StatisticsDTO extends RepresentationModel<StatisticsDTO> {
    }

    @Autowired
    private SaleQueryService saleQueryService;

    @Autowired
    private SaleReportService saleReportService;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @CheckSecurity.Statistics.CanQuery
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticsDTO statistics() {
        var statisticsDTO = new StatisticsDTO();

        statisticsDTO.add(algaLinks.linkToStatisticsDailySales("daily-sales"));

        return statisticsDTO;
    }

    @Override
    @CheckSecurity.Statistics.CanQuery
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> queryDailySales(DailySaleFilter filter,
                                           @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return saleQueryService.queryDailySales(filter, timeOffset);
    }

    @Override
    @CheckSecurity.Statistics.CanQuery
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter,
                                                     @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = saleReportService.issueDailySales(filter, timeOffset);
        var headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
    }
}
