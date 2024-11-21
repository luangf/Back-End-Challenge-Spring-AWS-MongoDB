package com.company.powerful_application.domain.category;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;

    public Category(CategoryRequestDTO categoryRequestDTO) {
        this.title = categoryRequestDTO.title();
        this.description = categoryRequestDTO.description();
        this.ownerId = categoryRequestDTO.ownerId();
    }

    @Override
    public String toString(){
        JSONObject json=new JSONObject();
        json.put("id", this.id);
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerId", this.ownerId);
        json.put("type", "category");
        return json.toString();
    }
}
