package ku.book.dto;


import lombok.Data;


@Data
public class BookResponse {
    private String name;
    private String author;
    private double price;
}

