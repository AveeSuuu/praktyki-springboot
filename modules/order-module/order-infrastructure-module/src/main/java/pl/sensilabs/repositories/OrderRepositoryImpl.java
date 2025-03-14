package pl.sensilabs.repositories;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.sensilabs.Order;
import pl.sensilabs.OrderRepository;
import pl.sensilabs.OrderStatus;
import pl.sensilabs.OrderEntity;
import pl.sensilabs.events.BookAddedToOrderEvent;
import pl.sensilabs.events.OrderStatusChangedEvent;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderRepositoryJpa jpaOrderRepository;

  @Override
  public Optional<Order> findOrderById(UUID orderId) {
    return jpaOrderRepository.findById(orderId).map(o ->
        new Order(orderId, o.getFinalPrice(), OrderStatus.valueOf(o.getOrderStatus())));
  }

  @Override
  public UUID saveOrder(Order order) {
    var entity = new OrderEntity(
        order.getFinalPrice(),
        order.getOrderStatus().toString());
    return jpaOrderRepository.save(entity).getOrderId();
  }

  @Override
  @Transactional
  public void apply(BookAddedToOrderEvent event) {
    jpaOrderRepository.findById(event.orderId()).ifPresent(order -> {
      order.setOrderStatus(event.status().toString());
      order.setFinalPrice(event.finalPrice());
    });
  }

  @Override
  @Transactional
  public void apply(OrderStatusChangedEvent event) {
    jpaOrderRepository.findById(event.orderId()).ifPresent(order ->
        order.setOrderStatus(event.status().toString()));
  }
}