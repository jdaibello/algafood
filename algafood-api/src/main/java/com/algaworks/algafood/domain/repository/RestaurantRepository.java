package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries,
        JpaSpecificationExecutor<Restaurant> {

    @Query("FROM Restaurant r JOIN FETCH r.kitchen")
    List<Restaurant> findAll();

    List<Restaurant> queryByShippingFeeBetween(BigDecimal initialFee, BigDecimal finalFee);

    // @Query("FROM Restaurante WHERE nome LIKE %:name% AND cozinha.id = :id")
    List<Restaurant> findByName(String name, @Param("id") Long kitchenId);

    List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);

    Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchenId);
}
