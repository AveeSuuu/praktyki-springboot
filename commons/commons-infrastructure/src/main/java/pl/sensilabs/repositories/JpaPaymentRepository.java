package pl.sensilabs.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sensilabs.entities.PaymentEntity;

@Repository
public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {

}
