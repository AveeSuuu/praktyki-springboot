package pl.sensilabs.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sensilabs.BasketItemEntity;

@Repository
public interface BasketRepositoryJpa extends JpaRepository<BasketItemEntity, UUID> {

  Optional<BasketItemEntity> findByOrderIdAndBookId(UUID orderId, UUID bookId);
}
