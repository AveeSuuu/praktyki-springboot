package pl.sensilabs.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public record BookAddRequest(
    @org.hibernate.validator.constraints.UUID
    String bookId,

    @Positive(message = "Quantity cannot be equal and less than 0")
    @Max(value = 10, message = "Cannot order more than 10 copies of the same book.")
    int quantity
) {

}
