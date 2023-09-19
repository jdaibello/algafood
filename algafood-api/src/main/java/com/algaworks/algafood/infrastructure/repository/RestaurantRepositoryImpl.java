package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withFreeShipping;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantSpecs.withSimilarName;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialShippingFee, BigDecimal finalShippingFee) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteria.from(Restaurant.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasLength(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (initialShippingFee != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("shippingFee"), initialShippingFee));
        }

        if (finalShippingFee != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("shippingFee"), finalShippingFee));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurant> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeShipping(String nome) {
        return restaurantRepository.findAll(withFreeShipping().and(withSimilarName(nome)));
    }
}
