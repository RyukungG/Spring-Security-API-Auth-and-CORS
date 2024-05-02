package ku.book.controller;


import jakarta.validation.Valid;
import ku.book.dto.BookRequest;
import ku.book.dto.BookResponse;
import ku.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookController {


    @Autowired
    private BookService service;


    @GetMapping
    public List<BookResponse> getAll() {
        return service.getAllBooks();
    }


    @PostMapping
    public ResponseEntity create(@Valid @RequestBody BookRequest bookRequest,
                                 BindingResult result) {


        if (result.hasErrors())
            return new ResponseEntity<String>("Invalid request format", HttpStatus.UNPROCESSABLE_ENTITY);


        service.addBook(bookRequest);
        return new ResponseEntity<BookRequest>(bookRequest, HttpStatus.OK);
    }
}
