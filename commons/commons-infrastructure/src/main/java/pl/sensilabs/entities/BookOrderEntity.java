package pl.sensilabs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "books_orders")
public class BookOrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID bookOrderId;
  UUID bookId;
  UUID orderId;
  Integer quantity;

  public BookOrderEntity(UUID bookId, UUID orderId, Integer quantity) {
    this.bookId = bookId;
    this.orderId = orderId;
    this.quantity = quantity;
  }
}
