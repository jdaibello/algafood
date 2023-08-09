package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByCode(String code);

    @Query("FROM Order o JOIN FETCH o.client JOIN FETCH o.restaurant r JOIN FETCH r.kitchen")
    List<Order> findAll();

    boolean isOrderManagedBy(String orderCode, Long userId);
}
