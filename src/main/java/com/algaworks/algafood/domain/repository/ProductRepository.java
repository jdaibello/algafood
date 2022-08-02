package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("FROM Product WHERE restaurant.id = :restaurant AND id = :product")
	Optional<Product> findById(@Param("restaurant") Long restaurantId, @Param("product") Long productId);

	List<Product> findAllByRestaurant(Restaurant restaurant);

	@Query("FROM Product p WHERE p.active = true AND p.restaurant = :restaurant")
	List<Product> findActivesByRestaurant(Restaurant restaurant);
}
