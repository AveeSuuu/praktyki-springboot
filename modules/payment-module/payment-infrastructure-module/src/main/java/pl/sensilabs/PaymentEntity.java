package pl.sensilabs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class PaymentEntity {

  @Id
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
