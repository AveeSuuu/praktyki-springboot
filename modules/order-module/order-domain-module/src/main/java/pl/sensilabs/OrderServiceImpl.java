package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.sensilabs.events.BookPriceRequestEvent;
import pl.sensilabs.events.OrderPaidEvent;
import pl.sensilabs.events.ProductAddedEvent;
import pl.sensilabs.exceptions.OrderNotFoundException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ApplicationEventPublisher publisher;

  @Override
  public UUID createOrder() {
    var order = new Order();
    return orderRepository.saveOrder(order);
  }

  @Override
  public Order getOrderById(UUID orderId) {
    return orderRepository.findOrderById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
  }

  @Override
  public void addBookToOrder(UUID orderId, UUID bookId, int quantity) {
    publisher.publishEvent(new ProductAddedEvent(orderId, bookId, quantity));
    var order = getOrderById(orderId);
    var bookPrice = new CompletableFuture<BigDecimal>();
    publisher.publishEvent(new BookPriceRequestEvent(bookId, bookPrice));
    bookPrice.whenComplete((price, throwable) -> {
      var event = order.addBookToBasket(price, quantity);
      orderRepository.apply(event);
    });
  }

  @Override
  public void finishOrder(UUID orderId) {
    var order = getOrderById(orderId);
    var event = order.finishOrder();
    orderRepository.apply(event);
  }

  @Override
  public void continueOrder(UUID orderId) {
    var order = getOrderById(orderId);
    var event = order.continueOrder();
    orderRepository.apply(event);
  }

  @Override
  public void payForOrder(UUID orderId) {
    var order = getOrderById(orderId);
    var event = order.payForOrder();
    publisher.publishEvent(new OrderPaidEvent(order.getOrderId(), order.getFinalPrice()));
    orderRepository.apply(event);
  }

  @Override
  public void shipOrder(UUID orderId) {
    var order = getOrderById(orderId);
    var event = order.shipOrder();
    orderRepository.apply(event);
  }

  @Override
  public void cancelOrder(UUID orderId) {
    var order = getOrderById(orderId);
    if (order.getOrderStatus() == OrderStatus.PAID) {
      refundPayment();
    }
    var event = order.cancelOrder();
    orderRepository.apply(event);
  }

  void refundPayment() {
    //tak dla sportu
  }
}
