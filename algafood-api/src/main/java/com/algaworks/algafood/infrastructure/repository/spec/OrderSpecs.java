package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

public class OrderSpecs {
    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();

            if (Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("kitchen");
                root.fetch("client");
            }

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client").get("id"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant").get("id"), filter.getRestaurantId()));
            }

            if (filter.getStartCreationDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getStartCreationDate()));
            }

            if (filter.getEndCreationDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getEndCreationDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
