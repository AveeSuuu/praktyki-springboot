package pl.sensilabs;

import java.util.UUID;

public interface BasketService {
  void addProductToOrder(UUID orderId, UUID bookId, int quantity);
}
