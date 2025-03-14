package pl.sensilabs.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import pl.sensilabs.BookPriceFetcher;
import pl.sensilabs.OrderServiceImpl;
import pl.sensilabs.OrderService;
import pl.sensilabs.exceptions.InvalidOrderStateException;
import pl.sensilabs.mocks.BookOrderRepositoryMock;
import pl.sensilabs.mocks.BookPriceFetcherMock;
import pl.sensilabs.mocks.OrderRepositoryMock;

class OrderServiceImplTest {

  private final BookPriceFetcher bookPriceFetcher;
  private final OrderService orderService;

  public OrderServiceImplTest() {
    bookPriceFetcher = new BookPriceFetcherMock();
    orderService = new OrderServiceImpl(
        new OrderRepositoryMock(),
        new BookOrderRepositoryMock(bookPriceFetcher),
        bookPriceFetcher, null);
  }

  @Test
  void createOrder_shouldCreateAndSaveNewOrderToRepository() {
    var order = orderService.createOrder();

    assertThat(order).isNotNull();

    var expectedOrder = orderService.getOrderById(order).getOrderId();

    assertThat(order).isSameAs(expectedOrder);
  }

  @Test
  void addBookToOrder_shouldAddBookToOrderAndRecalculatePrice() {
    var order = orderService.createOrder();
    var BOOK_ID = UUID.randomUUID();
    var bookPrice = bookPriceFetcher.fetch(BOOK_ID);

    orderService.addBookToOrder(order, BOOK_ID, 3);

    assertThat(orderService.getOrderById(order).getFinalPrice())
        .isEqualTo(bookPrice.multiply(new BigDecimal("3")));
  }

  @Test
  void payForOrder_whenDidntAddAnyBook_shouldDeclinePayment() {
    var orderId = orderService.createOrder();

    assertThatThrownBy(() -> orderService.payForOrder(orderId))
        .isInstanceOf(InvalidOrderStateException.class)
        .hasMessage("Cannot pay for unfinished order.");
  }
}
