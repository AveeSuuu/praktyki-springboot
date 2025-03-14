package pl.sensilabs.mappers;

import pl.sensilabs.Order;
import pl.sensilabs.responses.OrderDetailsResponse;

public interface OrderMapper {

  static OrderDetailsResponse toOrderDetailsResponse(Order order) {
    return OrderDetailsResponse.builder()
        .orderId(order.getOrderId())
        .finalPrice(order.getFinalPrice())
        .orderStatus(order.getOrderStatus().toString())
        .build();
  }
}
