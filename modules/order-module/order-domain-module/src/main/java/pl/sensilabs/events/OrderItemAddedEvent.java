package pl.sensilabs.events;

import java.math.BigDecimal;
import java.util.UUID;
import pl.sensilabs.OrderItem;
import pl.sensilabs.OrderStatus;

public record OrderItemAddedEvent(
    UUID orderId,
    OrderStatus status,
    BigDecimal finalPrice,
    OrderItem orderItem) {

}
