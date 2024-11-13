package com.company.powerful_application.domain.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.company.powerful_application.domain.category.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private Integer price;
    private Category category;
    private String ownerId;
}
