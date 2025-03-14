package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public BigDecimal getBookPrice(UUID bookId) {
    return bookRepository.fetchPrice(bookId);
  }
}
