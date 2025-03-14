package pl.sensilabs.mocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import pl.sensilabs.BookOrderRepository;
import pl.sensilabs.BookPriceFetcher;
import pl.sensilabs.OrderItem;

public class BookOrderRepositoryMock implements BookOrderRepository {

  private final Map<UUID, Set<OrderItem>> orderItems = new HashMap<>();
  private final BookPriceFetcher bookPriceFetcher;

  public BookOrderRepositoryMock(BookPriceFetcher bookPriceFetcher) {
    this.bookPriceFetcher = bookPriceFetcher;
  }

  @Override
  public void addBookOrder(UUID orderId, UUID bookId, int quantity) {
    if (!orderItems.containsKey(orderId)) {
      orderItems.put(orderId, new HashSet<>());
    }
    orderItems.get(orderId)
        .add(new OrderItem(
            bookId,
            quantity,
            bookPriceFetcher.fetch(bookId).toString()));
  }

  @Override
  public Set<OrderItem> getOrderBasket(UUID orderId) {
    return orderItems.get(orderId);
  }
}
