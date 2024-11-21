package com.company.powerful_application.domain.product;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String ownerId;
    private String categoryId;

    public Product(ProductRequestDTO productRequestDTO) {
        this.title = productRequestDTO.title();
        this.description = productRequestDTO.description();
        this.price = productRequestDTO.price();
        this.ownerId = productRequestDTO.ownerId();
        this.categoryId = productRequestDTO.categoryId();
    }

    @Override
    public String toString(){
        JSONObject json=new JSONObject();
        json.put("id", this.id);
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("price", this.price);
        json.put("ownerId", this.ownerId);
        json.put("categoryId", this.categoryId);
        json.put("type", "product");
        return json.toString();
    }
}
