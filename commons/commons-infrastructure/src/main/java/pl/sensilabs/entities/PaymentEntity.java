package pl.sensilabs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "payments")
@NoArgsConstructor
public class PaymentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID paymentId;
  private UUID orderId;
  private LocalDate receiveDate; //powinien być dateTime :clown:
  private BigDecimal price;

  public PaymentEntity(UUID orderId, BigDecimal price) {
    this.orderId = orderId;
    this.receiveDate = LocalDate.now();
    this.price = price;
  }
}
