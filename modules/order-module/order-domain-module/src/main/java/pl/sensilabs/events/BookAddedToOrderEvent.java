package pl.sensilabs.events;

import java.math.BigDecimal;
import java.util.UUID;
import pl.sensilabs.OrderStatus;

public record BookAddedToOrderEvent(
    UUID orderId,
    OrderStatus status,
    BigDecimal finalPrice) {

}