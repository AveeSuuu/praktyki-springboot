package pl.sensilabs.mocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import pl.sensilabs.Order;
import pl.sensilabs.OrderRepository;
import pl.sensilabs.events.OrderItemAddedEvent;
import pl.sensilabs.events.OrderStatusChangedEvent;

public class MockOrderRepository implements OrderRepository {

  Map<UUID, Order> orders = new HashMap<>();

  @Override
  public Optional<Order> findOrderById(UUID orderId) {
    return Optional.ofNullable(orders.get(orderId));
  }

  @Override
  public UUID saveOrder(Order order) {
    var newOrder = new Order(
        UUID.randomUUID(), order.getBasket(), order.getOrderStatus());
    orders.put(newOrder.getOrderId(), newOrder);
    return newOrder.getOrderId();
  }

  @Override
  public void apply(OrderItemAddedEvent event) {
    findOrderById(event.orderId()).ifPresent(order ->
        order.addBookToBasket(event.orderItem())
    );
  }

  @Override
  public void apply(OrderStatusChangedEvent event) {
    findOrderById(event.orderId()).ifPresent(order -> {
      switch (event.status()) {
        case AWAITING_PAYMENT -> order.finishOrder();
        case PROCESSING -> order.continueOrder();
        case PAID -> order.payForOrder();
        case SHIPPED -> order.shipOrder();
        case CANCELED -> order.cancelOrder();
      }
    });
  }


}
