package pl.sensilabs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "books")
public class BookEntity {

  @Id
  private UUID bookId;
  private String bookTitle;
  private Integer pages;
  private BigDecimal price;

  BookEntity(String bookTitle, Integer pages, BigDecimal price) {
    this.bookTitle = bookTitle;
    this.pages = pages;
    this.price = price;
  }
}
