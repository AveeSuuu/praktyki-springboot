package pl.sensilabs.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.sensilabs.BasketService;
import pl.sensilabs.events.ProductAddedEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class BasketEventListener {

  private final BasketService basketService;

  @EventListener
  void onProductAdded(ProductAddedEvent event) {
    log.info("Received ProductAdded event for order {}", event.orderId());
    basketService.addProductToOrder(event.orderId(), event.bookId(), event.quantity());
  }
}
