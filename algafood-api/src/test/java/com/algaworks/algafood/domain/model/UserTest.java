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
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext
public class UserTest {

    private Validator validator;
    private User user1;
    private User user2;
    private Group group1;
    private Group group2;
    private Group group3;


    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        group1 = new Group();
        group1.setId(1L);
        group1.setName("Admin");

        group2 = new Group();
        group2.setId(2L);
        group2.setName("Gerente");

        group3 = new Group();
        group3.setId(3L);
        group3.setName("Funcionário");

        user1 = new User();
        user1.setId(1L);
        user1.setName("João");
        user1.setEmail("joao@example.com");
        user1.setPassword("123456");
        user1.setCreationDate(OffsetDateTime.MIN);
        user1.setGroups(new HashSet<>(Set.of(group1, group2)));

        user2 = new User();
        user2.setId(2L);
        user2.setName("Maria");
        user2.setEmail("maria@example.com");
        user2.setPassword("123456");
        user1.setCreationDate(OffsetDateTime.MIN);
        user2.setGroups(new HashSet<>(Set.of(group2, group3)));
    }

    @Test
    public void testValidGroup() {
        Set<ConstraintViolation<User>> violations = validator.validate(user1);

        assertSame(user1.getId(), 1L);
        assertSame(user1.getName(), "João");
        assertSame(user1.getEmail(), "joao@example.com");
        assertSame(user1.getPassword(), "123456");
        assertSame(user1.getCreationDate(), OffsetDateTime.MIN);
        assertEquals(user1.getGroups(), Set.of(group1, group2));
        assertThat(violations).isEmpty();
    }

    @Test
    public void testAddGroup() {
        Group newGroup = new Group();
        newGroup.setId(4L);
        newGroup.setName("SuperUser");

        assertTrue(user2.addGroup(newGroup));
        assertTrue(user2.getGroups().contains(newGroup));
    }

    @Test
    public void testRemoveGroup() {
        assertTrue(user1.removeGroup(group1));
        assertFalse(user1.getGroups().contains(group1));
    }

    @Test
    public void testIsNew() {
        User newUser = new User();

        assertTrue(newUser.isNew());
        assertFalse(user1.isNew());
    }

    @Test
    public void testEqualsAndHashCode() {
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
