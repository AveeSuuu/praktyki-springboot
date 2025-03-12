package pl.sensilabs.events;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderPaidEvent(UUID orderId, BigDecimal price) {

}
