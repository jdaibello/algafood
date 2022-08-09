package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private Boolean active = Boolean.TRUE;

	private Boolean opened = Boolean.FALSE;

	@CreationTimestamp
	@Column(name = "creation_date", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime creationDate;

	@UpdateTimestamp
	@Column(name = "update_date", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updateDate;

	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	// Usar EAGER com muito cuidado em relações ManyToMany
	// @ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(
		name = "restaurant_payment_method",
		joinColumns = @JoinColumn(name = "restaurant_id"),
		inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private Set<PaymentMethod> paymentMethods = new HashSet<>();

	@ManyToMany
	@JoinTable(
		name = "restaurant_responsible_user",
		joinColumns = @JoinColumn(name = "restaurant_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> responsibles = new HashSet<>();

	public void activate() {
		setActive(true);
	}

	public void inactivate() {
		setActive(false);
	}

	public void open() {
		setOpened(true);
	}

	public void close() {
		setOpened(false);
	}

	public boolean addPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().add(paymentMethod);
	}

	public boolean removePaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().remove(paymentMethod);
	}

	public boolean acceptPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethods().contains(paymentMethod);
	}

	public boolean doesntAcceptPaymentMethod(PaymentMethod paymentMethod) {
		return !acceptPaymentMethod(paymentMethod);
	}

	public boolean addResponsible(User user) {
		return getResponsibles().add(user);
	}

	public boolean removeResponsible(User user) {
		return getResponsibles().remove(user);
	}
}
