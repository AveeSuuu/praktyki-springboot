package pl.sensilabs.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.sensilabs.BasketRepository;
import pl.sensilabs.BasketItemEntity;
import pl.sensilabs.events.ProductAddedEvent;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryImpl implements BasketRepository {

  private final BasketRepositoryJpa bookOrderRepositoryJpa;

  @Override
  @Transactional
  public void apply(ProductAddedEvent event) {
    bookOrderRepositoryJpa
        .findByOrderIdAndBookId(event.orderId(), event.bookId())
        .ifPresentOrElse(basketItem -> {
          var newQuantity = Math.min(10, basketItem.getQuantity() + event.quantity());
          basketItem.setQuantity(newQuantity);
        }, () -> bookOrderRepositoryJpa.save(
                new BasketItemEntity(event.bookId(), event.orderId(), event.quantity()))
        );
  }
}
