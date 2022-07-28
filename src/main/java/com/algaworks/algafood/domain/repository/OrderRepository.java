package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Order;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {

}
