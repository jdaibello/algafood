package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.service.KitchenService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KitchenIntegrationTests {

	@Autowired
	private KitchenService service;

	@Test
	public void shouldSaveKitchenSuccessfully() {
		// Cenário
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName("Australiana");

		// Ação
		newKitchen = service.save(newKitchen);

		// Validação
		assertThat(newKitchen).isNotNull();
		assertThat(newKitchen.getId()).isNotNull();
	}

	@Test
	public void shouldFailWhenSavingKitchenWithoutName() {
		Kitchen newKitchen = new Kitchen();
		newKitchen.setName(null);

		ConstraintViolationException expectedError = assertThrows(ConstraintViolationException.class, () -> {
			service.save(newKitchen);
		});

		assertThat(expectedError).isNotNull();
	}
}
