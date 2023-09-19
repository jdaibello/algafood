package com.algaworks.algafood.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class PaymentMethodTest {

    private Validator validator;
    private PaymentMethod paymentMethod1;
    private PaymentMethod paymentMethod2;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        paymentMethod1 = new PaymentMethod();
        paymentMethod1.setId(1L);
        paymentMethod1.setDescription("Cheque");
        paymentMethod1.setUpdateDate(OffsetDateTime.MIN);

        paymentMethod2 = new PaymentMethod();
        paymentMethod2.setId(2L);
        paymentMethod2.setDescription("PIX");
        paymentMethod2.setUpdateDate(OffsetDateTime.now());
    }

    @Test
    public void testValidPaymentMethod() {
        Set<ConstraintViolation<PaymentMethod>> violations = validator.validate(paymentMethod1);

        assertSame(paymentMethod1.getId(), 1L);
        assertSame(paymentMethod1.getDescription(), "Cheque");
        assertSame(paymentMethod1.getUpdateDate(), OffsetDateTime.MIN);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(paymentMethod1, paymentMethod2);
        assertNotEquals(paymentMethod1.hashCode(), paymentMethod2.hashCode());
    }
}
