package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.OrderNotFoundException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderIssuanceService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private CityService cityService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Transactional
	public Order issue(Order order) {
		validateOrder(order);
		validateItems(order);

		order.setShippingFee(order.getRestaurant().getShippingFee());
		order.calculateTotalValue();

		return orderRepository.save(order);
	}

	private void validateOrder(Order order) {
		City city = cityService.findOrFail(order.getDeliveryAddress().getCity().getId());
		User client = userService.findOrFail(order.getClient().getId());
		Restaurant restaurant = restaurantService.findOrFail(order.getRestaurant().getId());
		PaymentMethod paymentMethod = paymentMethodService.findOrFail(order.getPaymentMethod().getId());

		order.getDeliveryAddress().setCity(city);
		order.setClient(client);
		order.setRestaurant(restaurant);
		order.setPaymentMethod(paymentMethod);

		if (restaurant.doesntAcceptPaymentMethod(paymentMethod)) {
			throw new BusinessException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
					paymentMethod.getDescription()));
		}
	}

	private void validateItems(Order order) {
		order.getItems().forEach(item -> {
			Product product = productService.findOrFail(order.getRestaurant().getId(), item.getProduct().getId());

			item.setOrder(order);
			item.setProduct(product);
			item.setUnitPrice(product.getPrice());
		});
	}

	public Order findOrFail(String orderCode) {
		return orderRepository.findByCode(orderCode).orElseThrow(() -> new OrderNotFoundException(orderCode));
	}
}
