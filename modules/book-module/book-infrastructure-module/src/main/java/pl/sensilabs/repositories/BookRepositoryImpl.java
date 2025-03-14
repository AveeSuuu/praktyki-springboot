package pl.sensilabs.repositories;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.sensilabs.BookRepository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

  private final BookRepositoryJpa jpaBookRepository;

  @Override
  public BigDecimal fetchPrice(UUID bookId) {
    return jpaBookRepository.findByBookId(bookId).getPrice();
  }
}
