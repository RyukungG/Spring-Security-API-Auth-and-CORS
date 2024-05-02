package ku.book.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class BookRequest {


    @NotBlank
    private String name;


    @NotBlank
    private String author;


    @Positive
    private double price;
}
