package ku.book.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


import java.time.Instant;
import java.util.UUID;


@Data
@Entity
public class Book {


    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String author;
    private double price;
    private Instant createdAt;
}
