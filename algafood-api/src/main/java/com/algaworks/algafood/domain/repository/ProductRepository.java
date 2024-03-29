package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries {

    @Query("FROM Product WHERE restaurant.id = :restaurant AND id = :product")
    Optional<Product> findById(@Param("restaurant") Long restaurantId, @Param("product") Long productId);

    List<Product> findAllByRestaurant(Restaurant restaurant);

    @Query("FROM Product p WHERE p.active = true AND p.restaurant = :restaurant")
    List<Product> findActivesByRestaurant(Restaurant restaurant);

    @Query("SELECT p FROM ProductPhoto p JOIN p.product pr WHERE pr.restaurant.id = :restaurantId AND p.product.id = :productId")
    Optional<ProductPhoto> findPhotoById(Long restaurantId, Long productId);
}
