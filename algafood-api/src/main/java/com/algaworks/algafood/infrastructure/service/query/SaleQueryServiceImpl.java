package com.algaworks.algafood.infrastructure.service.query;

import com.algaworks.algafood.domain.filter.DailySaleFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.OrderStatus;
import com.algaworks.algafood.domain.model.dto.DailySale;
import com.algaworks.algafood.domain.service.SaleQueryService;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> queryDailySales(DailySaleFilter filter, String timeOffset) {
        var predicates = new ArrayList<Predicate>();

        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySale.class);
        var root = query.from(Order.class);

        var functionConvertTzCreationDate = builder.function("convert_tz", Date.class, root.get("creationDate"),
                builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateCreationDate = builder.function("date", Date.class, functionConvertTzCreationDate);

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant").get("id"), filter.getRestaurantId()));
        }

        if (filter.getStartCreationDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getStartCreationDate()));
        }

        if (filter.getEndCreationDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getEndCreationDate()));
        }

        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        var selection = builder.construct(DailySale.class, functionDateCreationDate, builder.count(root.get("id")),
                builder.sum(root.get("totalValue")));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateCreationDate);

        return manager.createQuery(query).getResultList();
    }
}
