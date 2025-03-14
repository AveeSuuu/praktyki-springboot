package pl.sensilabs.repositories;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.sensilabs.BookOrderRepository;
import pl.sensilabs.Order;
import pl.sensilabs.OrderBasket;
import pl.sensilabs.OrderRepository;
import pl.sensilabs.OrderStatus;
import pl.sensilabs.OrderEntity;
import pl.sensilabs.events.OrderItemAddedEvent;
import pl.sensilabs.events.OrderStatusChangedEvent;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderRepositoryJpa jpaOrderRepository;
  private final BookOrderRepository bookOrderRepository;

  @Override
  public Optional<Order> findOrderById(UUID orderId) {
    return jpaOrderRepository.findById(orderId).map(o -> {
      var orderBasket = new OrderBasket(bookOrderRepository.getOrderBasket(orderId));
      return new Order(
          orderId, orderBasket, OrderStatus.valueOf(o.getOrderStatus()));
    });
  }

  @Override
  public UUID saveOrder(Order order) {
    var entity = new OrderEntity();
    entity.setFinalPrice(order.getFinalPrice());
    entity.setOrderStatus(order.getOrderStatus().toString());
    return jpaOrderRepository.save(entity).getOrderId();
  }

  @Override
  @Transactional
  public void apply(OrderItemAddedEvent event) {
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