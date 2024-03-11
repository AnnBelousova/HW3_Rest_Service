package com.example.hw3_rest_service.mapper;

import com.example.hw3_rest_service.entity.Product;
import com.example.hw3_rest_service.model.dto.ProductDto;

public class MapperProduct {
    public ProductDto entityToDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setWeight(product.getWeight());
        return productDto;
    }

    public Product dtoToEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setWeight(productDto.getWeight());
        return product;
    }
}
