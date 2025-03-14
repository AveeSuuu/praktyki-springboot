package pl.sensilabs;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sensilabs.events.ProductAddedEvent;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

  private final BasketRepository basketRepository;

  @Override
  public void addProductToOrder(UUID orderId, UUID bookId, int quantity) {
    var event =  new ProductAddedEvent(orderId, bookId, quantity);
    basketRepository.apply(event);
  }
}
