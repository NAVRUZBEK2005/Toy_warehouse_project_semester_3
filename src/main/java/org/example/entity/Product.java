package org.example.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product  {
    private Integer id;
    private String name;
    private String description;
    private Long price;
    private Category category;
}

