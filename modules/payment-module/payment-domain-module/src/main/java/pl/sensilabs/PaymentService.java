package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {

  void registerPayment(UUID orderId, BigDecimal price);
}
