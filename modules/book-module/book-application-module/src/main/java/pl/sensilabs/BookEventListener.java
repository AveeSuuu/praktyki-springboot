package pl.sensilabs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.sensilabs.events.BookPriceRequestEvent;

@Component
@RequiredArgsConstructor
public class BookEventListener {

  private final BookService bookService;

  @EventListener
  void onBookPriceRequest(BookPriceRequestEvent event) {
    var price = bookService.getBookPrice(event.bookId());
    event.price().complete(price);
  }
}
