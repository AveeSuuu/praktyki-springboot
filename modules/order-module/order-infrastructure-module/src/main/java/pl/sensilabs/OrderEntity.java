package pl.sensilabs;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID orderId;
  BigDecimal finalPrice;
  String orderStatus;

  public OrderEntity(BigDecimal finalPrice, String orderStatus) {
    this.finalPrice = finalPrice;
    this.orderStatus = orderStatus;
  }
}
