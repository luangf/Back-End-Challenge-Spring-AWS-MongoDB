package com.company.powerful_application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.company.powerful_application.domain.category.Category;
import com.company.powerful_application.domain.category.CategoryRequestDTO;
import com.company.powerful_application.domain.category.CategoryResponseDTO;
import com.company.powerful_application.domain.category.exceptions.CategoryNotFoundException;
import com.company.powerful_application.repositories.CategoryRepository;
import com.company.powerful_application.services.aws.AwsSnsService;
import com.company.powerful_application.services.aws.MessageDTO;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final AwsSnsService awsSnsService;

    public CategoryService(CategoryRepository categoryRepository, AwsSnsService awsSnsService) {
        this.categoryRepository = categoryRepository;
        this.awsSnsService = awsSnsService;
    }

    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category(categoryRequestDTO);
        category = categoryRepository.save(category);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category.getId(), category.getTitle(),
                                                                          category.getDescription(), category.getOwnerId());
        
        awsSnsService.publish(new MessageDTO(category.toString()));
        
        return categoryResponseDTO;
    }

    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> categoriesResponseDTO = new ArrayList<>();

        categories.forEach(category -> {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category.getId(), category.getTitle(),
                                                                              category.getDescription(), category.getOwnerId());
            categoriesResponseDTO.add(categoryResponseDTO);
        });

        return categoriesResponseDTO;
    }

    public Optional<Category> getById(String id) {
        return this.categoryRepository.findById(id);
    }

    public CategoryResponseDTO update(String id, CategoryRequestDTO categoryRequestDTO){
        Category category=categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        if(!categoryRequestDTO.title().isEmpty()) category.setTitle(categoryRequestDTO.title());
        if(!categoryRequestDTO.description().isEmpty()) category.setDescription(categoryRequestDTO.description());

        categoryRepository.save(category);

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(category.getId(), category.getTitle(),
                                                                          category.getDescription(), category.getOwnerId());
        
        awsSnsService.publish(new MessageDTO(category.toString()));
        
        return categoryResponseDTO;
    }
    
    public void delete(@PathVariable("id") String id){
        categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.deleteById(id);
    }
}
