package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomainPaymentService implements PaymentService {

  private final PaymentRepository paymentRepository;

  @Override
  public void registerPayment(UUID orderId,  BigDecimal price) {
    paymentRepository.savePayment(orderId, price);
  }
}
