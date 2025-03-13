package pl.sensilabs.controllers;

import jakarta.validation.Valid;
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

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable UUID orderId) {
    var order = orderService.getOrderById(orderId);
    return ResponseEntity.ok(OrderMapper.toOrderDetailsResponse(order));
  }

  @PostMapping("/create")
  public ResponseEntity<UUID> createOrder() {
    var orderId = orderService.createOrder();
    return ResponseEntity.ok(orderId);
  }

  @PostMapping("/{orderId}/add")
  public ResponseEntity<Void> addBookToOrder(
      @PathVariable UUID orderId,
      @RequestBody @Valid BookAddRequest bookAddRequest) {
    orderService.addBookToOrder(orderId, bookAddRequest.bookId(), bookAddRequest.quantity());
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/{orderId}/finish")
  public ResponseEntity<Void> finishOrder(@PathVariable UUID orderId) {
    orderService.finishOrder(orderId);
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/{orderId}/continue")
  public ResponseEntity<Void> continueOrder(@PathVariable UUID orderId) {
    orderService.continueOrder(orderId);
    return ResponseEntity.accepted().build();
  }

  //myślałem nad utworzeniem mikro-serwisu do zarządzania płatnościami,
  //ale nie chcę aż tak daleko wychodzić z materiałem.
  @PostMapping("/{orderId}/pay")
  public ResponseEntity<Void> payForOrder(@PathVariable UUID orderId) {
    orderService.payForOrder(orderId);
    orderService.shipOrder(orderId);
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
    orderService.cancelOrder(orderId);
    return ResponseEntity.accepted().build();
  }
}
