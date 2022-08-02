package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;

public class OrderSpecs {
	public static Specification<Order> usingFilter(OrderFilter filter) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			root.fetch("restaurant").fetch("kitchen");
			root.fetch("client");

			if (filter.getClientId() != null) {
				predicates.add(builder.equal(root.get("client"), filter.getClientId()));
			}

			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
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
