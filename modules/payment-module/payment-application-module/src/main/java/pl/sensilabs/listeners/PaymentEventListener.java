package pl.sensilabs.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.sensilabs.PaymentService;
import pl.sensilabs.events.OrderPaidEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventListener {

  private final PaymentService paymentService;

  @EventListener
  void onOrderPaid(OrderPaidEvent orderPaidEvent) {
    log.info("Received OrderPaid event for order: {}", orderPaidEvent.orderId());
    paymentService.registerPayment(orderPaidEvent.orderId(), orderPaidEvent.price());
  }
}
