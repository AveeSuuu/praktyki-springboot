package pl.sensilabs.mocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import pl.sensilabs.BookPriceFetcher;

public class BookPriceFetcherMock implements BookPriceFetcher {

  Map<UUID, BigDecimal> bookPrices = new HashMap<>();
  Random random = new Random();

  @Override
  public BigDecimal fetch(UUID orderId) {
    return bookPrices.computeIfAbsent(orderId,
        id -> BigDecimal.valueOf(random.nextDouble() * 1000)
            .setScale(2, RoundingMode.HALF_UP));
  }
}
