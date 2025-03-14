package pl.sensilabs.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sensilabs.entities.BookOrderEntity;

@Repository
public interface BookOrderRepositoryJpa extends JpaRepository<BookOrderEntity, UUID> {

  List<BookOrderEntity> findAllByOrderId(UUID orderId);

  boolean existsByOrderIdAndBookId(UUID orderId, UUID bookId);

  BookOrderEntity findByOrderIdAndBookId(UUID orderId, UUID bookId);
}
