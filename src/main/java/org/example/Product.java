package org.example;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

