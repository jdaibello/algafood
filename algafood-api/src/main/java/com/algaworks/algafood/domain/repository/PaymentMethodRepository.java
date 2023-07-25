package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query("SELECT max(updateDate) FROM PaymentMethod")
    OffsetDateTime getLastUpdateDate();

    @Query("SELECT updateDate FROM PaymentMethod WHERE id = :paymentMethodId")
    OffsetDateTime getUpdateDateById(Long paymentMethodId);
}
