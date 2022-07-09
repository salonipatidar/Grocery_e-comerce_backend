package com.ecommerce.grocery.service;

import com.ecommerce.grocery.dto.ProductDto;
import com.ecommerce.grocery.exceptions.ProductNotExistsException;
import com.ecommerce.grocery.model.Category;
import com.ecommerce.grocery.model.Product;
import com.ecommerce.grocery.repository.CategoryRepo;
import com.ecommerce.grocery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository ;

    @Autowired
    CategoryRepo categoryRepo ;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setImageURL(product.getImageUrl());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts(Integer categoryId) {
        List<Product> allProducts = productRepository.findAllByCategoryId(categoryId);

        List<ProductDto>  productDtos = new ArrayList<>();
        for(Product product : allProducts)
            productDtos.add(getProductDto(product));

        return  productDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(!optionalProduct.isPresent())
            throw  new Exception();

        Optional<Category> category = categoryRepo.findById(productDto.getCategoryId());

        if(!category.isPresent())
            throw  new Exception();

        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category.get());
        productRepository.save(product);

    }

    public Product findById(Integer productId) throws ProductNotExistsException{
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty())
            throw  new ProductNotExistsException("Product id is invalid" + productId);
        return optionalProduct.get();
    }

    public ProductDto getProduct(Integer productId) {

       Product product = findById(productId);
        return getProductDto(product);
    }
}
