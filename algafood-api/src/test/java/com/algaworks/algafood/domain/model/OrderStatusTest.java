package com.algaworks.algafood.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class OrderStatusTest {

    @Test
    public void testOrderStatusDescription() {
        assertThat(OrderStatus.CREATED.getDescription()).isEqualTo("Criado");
        assertThat(OrderStatus.CONFIRMED.getDescription()).isEqualTo("Confirmado");
        assertThat(OrderStatus.DELIVERED.getDescription()).isEqualTo("Entregue");
        assertThat(OrderStatus.CANCELLED.getDescription()).isEqualTo("Cancelado");
    }

    @Test
    public void testCanChangeTo() {
        assertThat(OrderStatus.CREATED.canChangeTo(OrderStatus.CONFIRMED)).isTrue();
        assertThat(OrderStatus.CONFIRMED.canChangeTo(OrderStatus.DELIVERED)).isTrue();
    }

    @Test
    public void testCantChangeTo() {
        assertThat(OrderStatus.CREATED.cantChangeTo(OrderStatus.CONFIRMED)).isFalse();
        assertThat(OrderStatus.CONFIRMED.cantChangeTo(OrderStatus.DELIVERED)).isFalse();
    }
}
