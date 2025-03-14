package pl.sensilabs.repositories;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sensilabs.entities.BookEntity;

@Repository
public interface BookRepositoryJpa extends JpaRepository<BookEntity, UUID> {

  BookPrice findByBookId(UUID bookId);

  interface BookPrice {
    BigDecimal getPrice();
  }
}
