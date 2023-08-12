package com.algaworks.algafood.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class AddressTest {

    private Validator validator;
    private City city;
    private Address address1;
    private Address address2;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        city = new City();
        city.setId(1L);
        city.setName("São Paulo");

        address1 = new Address();
        address1.setZipCode("38400-111");
        address1.setStreet("Rua Acre");
        address1.setNumber("300");
        address1.setComplement("Casa 2");
        address1.setDistrict("Centro");
        address1.setCity(city);

        address2 = new Address();
        address2.setZipCode("38400-112");
        address2.setStreet("Rua Amazonas");
        address2.setNumber("301");
        address2.setComplement("Casa Z");
        address2.setDistrict("Centro");
        address2.setCity(city);
    }

    @Test
    public void testValidAddress() {
        Set<ConstraintViolation<Address>> violations = validator.validate(address1);

        assertSame(address1.getZipCode(), "38400-111");
        assertSame(address1.getStreet(), "Rua Acre");
        assertSame(address1.getNumber(), "300");
        assertSame(address1.getComplement(), "Casa 2");
        assertSame(address1.getDistrict(), "Centro");
        assertSame(address1.getCity(), city);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(address1, address2);
        assertNotEquals(address1.hashCode(), address2.hashCode());
    }
}
