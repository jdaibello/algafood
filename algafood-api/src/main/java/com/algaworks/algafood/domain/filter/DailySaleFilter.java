package com.algaworks.algafood.domain.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.OffsetDateTime;

@Getter
@Setter
public class DailySaleFilter {
    private Long restaurantId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime startCreationDate;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime endCreationDate;
}
