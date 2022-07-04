package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.ZeroValueIncludesDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ZeroValueIncludesDescription(
	fieldValue = "shippingFee",
	fieldDescription = "name",
	mandatoryDescription = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "shipping_fee", nullable = false)
	private BigDecimal shippingFee;

	@ManyToOne
	@JoinColumn(name = "kitchen_id", nullable = false)
	private Kitchen kitchen;

	@Embedded
	private Address address;

	@CreationTimestamp
	@Column(name = "creation_date", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime creationDate;

	@UpdateTimestamp
	@Column(name = "update_date", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updateDate;

	@OneToMany
	@JoinTable(name = "restaurant_product")
	private List<Product> products = new ArrayList<>();

	// Usar EAGER com muito cuidado em relações ManyToMany
	// @ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(
		name = "restaurant_payment_method",
		joinColumns = @JoinColumn(name = "restaurant_id"),
		inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private List<PaymentMethod> paymentMethods = new ArrayList<>();
}
