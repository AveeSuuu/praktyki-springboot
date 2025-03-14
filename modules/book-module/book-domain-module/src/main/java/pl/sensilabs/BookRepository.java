package pl.sensilabs;

import java.math.BigDecimal;
import java.util.UUID;

public interface BookRepository {

  BigDecimal fetchPrice(UUID bookId);
}
