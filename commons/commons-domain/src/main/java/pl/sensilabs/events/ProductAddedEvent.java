package pl.sensilabs.events;

import java.util.UUID;

public record ProductAddedEvent(
    UUID orderId,
    UUID bookId,
    int quantity) {

}
