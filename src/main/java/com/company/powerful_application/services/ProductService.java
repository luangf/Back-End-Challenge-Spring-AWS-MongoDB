package com.company.powerful_application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.company.powerful_application.domain.category.exceptions.CategoryNotFoundException;
import com.company.powerful_application.domain.product.Product;
import com.company.powerful_application.domain.product.ProductRequestDTO;
import com.company.powerful_application.domain.product.ProductResponseDTO;
import com.company.powerful_application.domain.product.exceptions.ProductNotFoundException;
import com.company.powerful_application.repositories.ProductRepository;
import com.company.powerful_application.services.aws.AwsSnsService;
import com.company.powerful_application.services.aws.MessageDTO;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AwsSnsService awsSnsService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, AwsSnsService awsSnsService){
        this.productRepository=productRepository;
        this.categoryService=categoryService;
        this.awsSnsService=awsSnsService;
    }
    
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        this.categoryService.getById(productRequestDTO.categoryId())
                                              .orElseThrow(CategoryNotFoundException::new);

        Product product = new Product(productRequestDTO);

        product = productRepository.save(product);

        awsSnsService.publish(new MessageDTO(product.toString()));

        ProductResponseDTO productResponseDTO = new ProductResponseDTO(product.getId(), product.getTitle(),
                                                                       product.getDescription(), product.getPrice(),
                                                                       product.getCategoryId(), product.getOwnerId());
        return productResponseDTO;
    }

    public List<ProductResponseDTO> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productsResponseDTO = new ArrayList<>();

        products.forEach(product -> {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO(product.getId(), product.getTitle(),
                                                                           product.getDescription(), product.getPrice(),
                                                                           product.getCategoryId(), product.getOwnerId());
            productsResponseDTO.add(productResponseDTO);
        });

        return productsResponseDTO;
    }

    public ProductResponseDTO update(String id, ProductRequestDTO productRequestDTO){
        Product product=productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if(productRequestDTO.categoryId() != null){
            // verify if category exists in db
            this.categoryService.getById(productRequestDTO.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
            product.setCategoryId(productRequestDTO.categoryId());
        }

        if(!productRequestDTO.title().isEmpty()) product.setTitle(productRequestDTO.title());
        if(!productRequestDTO.description().isEmpty()) product.setDescription(productRequestDTO.description());
        if(!(productRequestDTO.price() == null)) product.setPrice(productRequestDTO.price());

        productRepository.save(product);

        awsSnsService.publish(new MessageDTO(product.toString()));

        ProductResponseDTO productResponseDTO = new ProductResponseDTO(product.getId(), product.getTitle(),
                                                                       product.getDescription(), product.getPrice(),
                                                                       product.getCategoryId(), product.getOwnerId());

        return productResponseDTO;
    }
    
    public void delete(@PathVariable("id") String id){
        productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
    }
}
