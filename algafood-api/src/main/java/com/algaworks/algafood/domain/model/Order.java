package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.CancelledOrderEvent;
import com.algaworks.algafood.domain.event.ConfirmedOrderEvent;
import com.algaworks.algafood.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "`order`")
public class Order extends AbstractAggregateRoot<Order> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalValue;

    @Embedded
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancellationDate;
    private OffsetDateTime deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public void calculateTotalValue() {
        getItems().forEach(OrderItem::calculateTotalValue);

        this.subtotal = getItems().stream().map(item -> item.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalValue = this.subtotal.add(this.shippingFee);
    }

    public void confirm() {
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());

        registerEvent(new ConfirmedOrderEvent(this));
    }

    public void delivery() {
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELED);
        setCancellationDate(OffsetDateTime.now());

        registerEvent(new CancelledOrderEvent(this));
    }

    private void setStatus(OrderStatus newStatus) {
        if (getStatus().cantChangeTo(newStatus)) {
            throw new BusinessException(String.format("O status do pedido %s não pode ser alterado de %s para %s",
                    getCode(), getStatus().getDescription(), newStatus.getDescription()));
        }

        this.status = newStatus;
    }

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
