package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "`order`")
public class Order {

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
	private OrderStatus status;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
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

	@OneToMany(mappedBy = "order")
	private List<OrderItem> items = new ArrayList<>();

	public void calculateTotalValue() {
		this.subtotal = getItems().stream().map(item -> item.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.totalValue = this.subtotal.add(this.shippingFee);
	}

	public void defineShippingFee() {
		setShippingFee(getRestaurant().getShippingFee());
	}

	public void assignOrderToItems() {
		getItems().forEach(item -> item.setOrder(this));
	}

	public void confirm() {
		setStatus(OrderStatus.CONFIRMED);
		setConfirmationDate(OffsetDateTime.now());
	}

	public void delivery() {
		setStatus(OrderStatus.DELIVERED);
		setDeliveryDate(OffsetDateTime.now());
	}

	public void cancel() {
		setStatus(OrderStatus.CANCELED);
		setCancellationDate(OffsetDateTime.now());
	}

	private void setStatus(OrderStatus newStatus) {
		if (getStatus().cantChangeTo(newStatus)) {
			throw new BusinessException(String.format("O status do pedido %s não pode ser alterado de %s para %s",
					getCode(),
					getStatus().getDescription(),
					newStatus.getDescription()));
		}

		this.status = newStatus;
	}

	@PrePersist
	private void generateCode() {
		setCode(UUID.randomUUID().toString());
	}
}
