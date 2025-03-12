package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentRepository {
  void savePayment(UUID orderId, BigDecimal price);
}
