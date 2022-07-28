package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "`order`")
public class Order {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@ManyToOne
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
}
