package pl.sensilabs.events;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public record BookPriceRequestEvent(
    UUID bookId,
    CompletableFuture<BigDecimal> price) {

}
