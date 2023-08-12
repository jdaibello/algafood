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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class StateTest {

    private Validator validator;
    private State state1;
    private State state2;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        state1 = new State();
        state1.setId(1L);
        state1.setName("São Paulo");

        state2 = new State();
        state2.setId(2L);
        state2.setName("Sergipe");
    }

    @Test
    public void testValidState() {
        Set<ConstraintViolation<State>> violations = validator.validate(state1);

        assertSame(state1.getId(), 1L);
        assertSame(state1.getName(), "São Paulo");
        assertThat(violations).isEmpty();
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(state1, state2);
        assertNotEquals(state1.hashCode(), state2.hashCode());
    }
}
