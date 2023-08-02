package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.controller.StatisticsController;
import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.dto.DailySale;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface StatisticsControllerOpenApi {

    @ApiOperation(value = "Estatísticas", hidden = true)
    StatisticsController.StatisticsDTO statistics();

    @ApiOperation("Consultar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "ID do restaurante", example = "1", dataType = "int"),
            @ApiImplicitParam(
                    name = "startCreationDate",
                    value = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z",
                    dataType = "date-time"),
            @ApiImplicitParam(
                    name = "endCreationDate",
                    value = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z",
                    dataType = "date-time")})
    List<DailySale> queryDailySales(DailySaleFilter filter,
                                    @ApiParam(
                                            value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                                            defaultValue = "+00:00") String timeOffset);

    ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter filter, String timeOffset);

}