package pl.sensilabs;

import java.util.Optional;
import java.util.UUID;
import pl.sensilabs.events.BookAddedToOrderEvent;
import pl.sensilabs.events.OrderStatusChangedEvent;

public interface OrderRepository {

  Optional<Order> findOrderById(UUID orderId);

  UUID saveOrder(Order order);

  void apply(BookAddedToOrderEvent event);

  void apply(OrderStatusChangedEvent event);
}
