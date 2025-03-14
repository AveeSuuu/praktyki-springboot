package pl.sensilabs.repositories;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.sensilabs.PaymentRepository;
import pl.sensilabs.PaymentEntity;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

  private final PaymentRepositoryJpa jpaPaymentRepository;

  @Override
  public void savePayment(UUID orderId, BigDecimal price) {
    jpaPaymentRepository.save(new PaymentEntity(orderId, price));
  }
}
