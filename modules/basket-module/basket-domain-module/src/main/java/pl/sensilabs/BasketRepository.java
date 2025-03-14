package pl.sensilabs;

import pl.sensilabs.events.ProductAddedEvent;

public interface BasketRepository {
  void apply(ProductAddedEvent event);
}
