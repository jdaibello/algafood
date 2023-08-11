package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.controller.StatisticsController;
import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Estatísticas")
public interface StatisticsControllerOpenApi {

    @Operation(summary = "Estatísticas", hidden = true)
    StatisticsController.StatisticsDTO statistics();

    @Operation(summary = "Consultar")
    @Parameters({
            @Parameter(name = "restaurantId", description = "ID do restaurante", example = "1"),
            @Parameter(
                    name = "startCreationDate",
                    description = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z"),
            @Parameter(
                    name = "endCreationDate",
                    description = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z")})
    List<DailySale> queryDailySales(DailySaleFilter filter,
                                    @Parameter(
                                            description = "Deslocamento de horário a ser considerado na consulta em relação ao" +
                                                    " UTC",
                                            example = "+00:00") String timeOffset);

    ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter, String timeOffset);

}