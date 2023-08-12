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
public class CityTest {

    private Validator validator;
    private City city1;
    private City city2;
    private State state;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        state = new State();
        state.setId(1L);
        state.setName("São Paulo");

        city1 = new City();
        city1.setId(1L);
        city1.setName("São Paulo");
        city1.setState(state);

        city2 = new City();
        city2.setId(2L);
        city2.setName("Guarulhos");
        city1.setState(state);
    }

    @Test
    public void testValidCity() {
        Set<ConstraintViolation<City>> violations = validator.validate(city1);

        assertSame(city1.getId(), 1L);
        assertSame(city1.getName(), "São Paulo");
        assertSame(city1.getState(), state);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(city1, city2);
        assertNotEquals(city1.hashCode(), city2.hashCode());
    }
}
