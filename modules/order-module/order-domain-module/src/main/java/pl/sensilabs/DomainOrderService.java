package pl.sensilabs;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.sensilabs.events.OrderPaidEvent;
import pl.sensilabs.exceptions.OrderNotFoundException;

@Service
@RequiredArgsConstructor
public class DomainOrderService implements OrderService {

  private final OrderRepository orderRepository;
  private final BookOrderRepository bookOrderRepository;
  private final BookPriceFetcher bookPriceFetcher;
  private final ApplicationEventPublisher publisher;

  @Override
  public UUID createOrder() {
    return orderRepository.saveOrder(new Order());
  }

  @Override
  public Order getOrderById(UUID orderId) {
    return orderRepository.findOrderById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));
  }

  @Override
  public void addBookToOrder(UUID orderId, UUID bookId, int quantity) {
    var order = getOrderById(orderId);
    var bookPrice = bookPriceFetcher.fetch(bookId).toString();
    var orderItem = new OrderItem(bookId, quantity, bookPrice);
    var event = order.addBookToBasket(orderItem);
    bookOrderRepository.addBookOrder(orderId, bookId, quantity);
    orderRepository.apply(event);
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
