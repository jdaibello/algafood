package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Order;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	Optional<Order> findByCode(String code);

	@Query("FROM Order o JOIN FETCH o.client JOIN FETCH o.restaurant r JOIN FETCH r.kitchen")
	List<Order> findAll();
}
