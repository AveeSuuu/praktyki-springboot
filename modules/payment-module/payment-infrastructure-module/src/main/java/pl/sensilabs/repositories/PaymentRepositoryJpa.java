package pl.sensilabs.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sensilabs.PaymentEntity;

@Repository
public interface PaymentRepositoryJpa extends JpaRepository<PaymentEntity, UUID> {

}
