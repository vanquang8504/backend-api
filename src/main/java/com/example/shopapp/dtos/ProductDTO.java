package com.example.shopapp.dtos;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3,max = 200, message = "Title must between 3 and 200 characters")
    private String name;

    @Min(value = 0,message = "Price must be greater than or equal to 0")
    @Max(value = 10000000, message = "Price be less than or equal to 10.000.000")
    private float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private long categoryId;

}
