package pl.sensilabs.events;

import java.util.UUID;
import pl.sensilabs.OrderStatus;

public record OrderStatusChangedEvent(UUID orderId, OrderStatus status) {

}
