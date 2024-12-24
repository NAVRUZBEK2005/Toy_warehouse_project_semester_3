package org.example;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category   {

    private Integer id;
    private String name;
    private String description;
    private Category parent;
    private List<Category> children;

}
