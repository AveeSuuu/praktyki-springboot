package pl.sensilabs.praktyki.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookTypeNotFoundException extends RuntimeException {


    public BookTypeNotFoundException(int id) {
        super("Could not find book type with id " + id);
    }
}
