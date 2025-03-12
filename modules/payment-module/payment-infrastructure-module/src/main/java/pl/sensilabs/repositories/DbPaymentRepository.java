package pl.sensilabs.repositories;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.sensilabs.PaymentRepository;
import pl.sensilabs.entities.PaymentEntity;

@Repository
@RequiredArgsConstructor
public class DbPaymentRepository implements PaymentRepository {

  private final JpaPaymentRepository jpaPaymentRepository;

  @Override
  public void savePayment(UUID orderId, BigDecimal price) {
    jpaPaymentRepository.save(new PaymentEntity(orderId, price));
  }
}
