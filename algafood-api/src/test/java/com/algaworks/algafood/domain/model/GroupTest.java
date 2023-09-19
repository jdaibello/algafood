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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class GroupTest {

    private Validator validator;
    private Permission permission1;
    private Permission permission2;
    private Permission permission3;
    private Group group1;
    private Group group2;


    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        permission1 = new Permission();
        permission1.setId(1L);
        permission1.setName("CONSULTAR_COZINHAS");
        permission1.setDescription("Permite consultar cozinhas");

        permission2 = new Permission();
        permission2.setId(2L);
        permission2.setName("EDITAR_COZINHAS");
        permission2.setDescription("Permite editar cozinhas");

        permission3 = new Permission();
        permission3.setId(3L);
        permission3.setName("EDITAR_RESTAURANTES");
        permission3.setDescription("Permite editar restaurantes");

        group1 = new Group();
        group1.setId(1L);
        group1.setName("Cadastrador");
        group1.setPermissions(new HashSet<>(Set.of(permission1, permission2)));

        group2 = new Group();
        group2.setId(2L);
        group2.setName("Gerente");
        group2.setPermissions(new HashSet<>(Set.of(permission3)));
    }

    @Test
    public void testValidGroup() {
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission1);

        assertSame(group1.getId(), 1L);
        assertSame(group1.getName(), "Cadastrador");
        assertEquals(group1.getPermissions(), Set.of(permission1, permission2));
        assertThat(violations).isEmpty();
    }

    @Test
    public void testAddPermission() {
        assertTrue(group2.addPermission(permission1));
        assertTrue(group2.getPermissions().contains(permission1));
    }

    @Test
    public void testRemovePermission() {
        assertTrue(group1.removePermission(permission1));
        assertFalse(group1.getPermissions().contains(permission1));
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(group1, group2);
        assertNotEquals(group1.hashCode(), group2.hashCode());
    }
}
