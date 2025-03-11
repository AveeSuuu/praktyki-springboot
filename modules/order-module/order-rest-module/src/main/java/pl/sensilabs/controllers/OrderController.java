package pl.sensilabs.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sensilabs.OrderService;
import pl.sensilabs.mappers.OrderMapper;
import pl.sensilabs.requests.BookAddRequest;
import pl.sensilabs.responses.OrderDetailsResponse;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/create")
  public ResponseEntity<UUID> createOrder() {
    var orderId = orderService.createOrder();
    return ResponseEntity.ok(orderId);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable UUID orderId) {
    var order = orderService.getOrderById(orderId);
    return ResponseEntity.ok(OrderMapper.toOrderDetailsResponse(order));
  }

  @PostMapping("/{orderId}/add")
  public ResponseEntity<Void> addBookToOrder(
      @PathVariable UUID orderId,
      @RequestBody BookAddRequest bookAddRequest) {
    orderService.addBookToOrder(orderId, bookAddRequest.bookId(), bookAddRequest.quantity());
    return ResponseEntity.accepted().build();
  }
}
