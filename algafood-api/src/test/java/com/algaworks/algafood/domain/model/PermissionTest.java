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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class PermissionTest {

    private Validator validator;
    private Permission permission1;
    private Permission permission2;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        permission1 = new Permission();
        permission1.setId(1L);
        permission1.setName("CONSULTAR_RESTAURANTES");
        permission1.setDescription("Permite consultar restaurantes");

        permission2 = new Permission();
        permission2.setId(2L);
        permission2.setName("EDITAR_PRODUTOS");
        permission2.setDescription("Permite editar produtos");
    }

    @Test
    public void testValidPermission() {
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission1);

        assertSame(permission1.getId(), 1L);
        assertSame(permission1.getName(), "CONSULTAR_RESTAURANTES");
        assertSame(permission1.getDescription(), "Permite consultar restaurantes");
        assertThat(violations).isEmpty();
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(permission1, permission2);
        assertNotEquals(permission1.hashCode(), permission2.hashCode());
    }
}
