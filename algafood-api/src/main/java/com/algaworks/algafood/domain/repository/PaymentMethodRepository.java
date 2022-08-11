package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

	@Query("SELECT max(updateDate) FROM PaymentMethod")
	OffsetDateTime getLastUpdateDate();

	@Query("SELECT updateDate FROM PaymentMethod WHERE id = :paymentMethodId")
	OffsetDateTime getUpdateDateById(Long paymentMethodId);
}
