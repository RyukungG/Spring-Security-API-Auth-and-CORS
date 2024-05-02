
package ku.book.service;


import ku.book.dto.BookRequest;
import ku.book.dto.BookResponse;
import ku.book.entity.Book;
import ku.book.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {


    @Autowired
    private BookRepository repository;


    @Autowired
    private ModelMapper modelMapper;

    public List<BookResponse> getAllBooks() {
        List<Book> daos = repository.findAll();


        List<BookResponse> dtos = daos
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .collect(Collectors.toList());


        return dtos;
    }


    public void addBook(BookRequest dto) {
        Book dao = modelMapper.map(dto, Book.class);
        dao.setCreatedAt(Instant.now());
        repository.save(dao);
    }
}

