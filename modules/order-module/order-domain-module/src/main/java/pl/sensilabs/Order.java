package pl.sensilabs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.Getter;
import pl.sensilabs.events.BookAddedToOrderEvent;
import pl.sensilabs.events.OrderStatusChangedEvent;
import pl.sensilabs.exceptions.InvalidOrderStateException;

@Getter
public class Order {

  private UUID orderId;
  private BigDecimal finalPrice;
  private OrderStatus orderStatus;

  public Order() {
    finalPrice = BigDecimal.ZERO;
    orderStatus = OrderStatus.CONFIRMED;
  }

  public Order(UUID orderId, BigDecimal finalPrice, OrderStatus orderStatus) {
    this.orderId = orderId;
    this.finalPrice = finalPrice;
    this.orderStatus = orderStatus;
  }

  public BookAddedToOrderEvent addBookToBasket(BigDecimal price, int quantity) {
    if (!canAddProductToOrder()) {
      throw new InvalidOrderStateException("Order has already been finished and ready for payment");
    }
    orderStatus = OrderStatus.PROCESSING;
    recalculateFinalPrice(price, quantity);
    return new BookAddedToOrderEvent(orderId, orderStatus, finalPrice);
  }

  private boolean canAddProductToOrder() {
    return orderStatus == OrderStatus.CONFIRMED || orderStatus == OrderStatus.PROCESSING;
  }

  private void recalculateFinalPrice(BigDecimal price, int quantity) {
    this.finalPrice = this.finalPrice
        .add(price.multiply(BigDecimal.valueOf(quantity)))
        .setScale(2, RoundingMode.HALF_UP);
  }

  public OrderStatusChangedEvent finishOrder() {
    validateState(OrderStatus.PROCESSING, "Cannot finish empty order.");
    orderStatus = OrderStatus.AWAITING_PAYMENT;
    return new OrderStatusChangedEvent(orderId, orderStatus);
  }

  public OrderStatusChangedEvent continueOrder() {
    validateState(
        OrderStatus.AWAITING_PAYMENT,
        "Cannot continue order that is unfinished, paid or shipped.");
    orderStatus = OrderStatus.PROCESSING;
    return new OrderStatusChangedEvent(orderId, orderStatus);
  }

  public OrderStatusChangedEvent payForOrder() {
    validateState(OrderStatus.AWAITING_PAYMENT, "Cannot pay for unfinished order.");
    orderStatus = OrderStatus.PAID;
    return new OrderStatusChangedEvent(orderId, orderStatus);
  }

  public OrderStatusChangedEvent shipOrder() {
    validateState(OrderStatus.PAID, "Cannot ship order until its paid.");
    orderStatus = OrderStatus.SHIPPED;
    return new OrderStatusChangedEvent(orderId, orderStatus);
  }

  public OrderStatusChangedEvent cancelOrder() {
    if (!canCancelOrder()) {
      throw new InvalidOrderStateException("Cannot cancel order awaiting for payment.");
    }
    orderStatus = OrderStatus.CANCELED;
    return new OrderStatusChangedEvent(orderId, orderStatus);
  }

  private boolean canCancelOrder() {
    return orderStatus == OrderStatus.CONFIRMED
        || orderStatus == OrderStatus.PROCESSING
        || orderStatus == OrderStatus.AWAITING_PAYMENT
        || orderStatus == OrderStatus.SHIPPED;
  }

  private void validateState(OrderStatus expectedStatus, String exceptionMessage) {
    if (orderStatus != expectedStatus) {
      throw new InvalidOrderStateException(exceptionMessage);
    }
  }
}
