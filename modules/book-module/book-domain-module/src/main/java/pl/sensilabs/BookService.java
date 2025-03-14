package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;

public interface BookService {
  BigDecimal getBookPrice(UUID bookId);
}
