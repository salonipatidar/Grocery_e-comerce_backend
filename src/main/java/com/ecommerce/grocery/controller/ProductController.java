package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.common.ApiResponse;
import com.ecommerce.grocery.dto.ProductDto;
import com.ecommerce.grocery.model.Category;
import com.ecommerce.grocery.repository.CategoryRepo;
import com.ecommerce.grocery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "https://salonipatidar.github.io/Grocery_e-commerce_frontend")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;




    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        if(!optionalCategory.isPresent())
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category does not exists") ,headers,HttpStatus.BAD_REQUEST);

        productService.createProduct(productDto , optionalCategory.get());
        return  new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product created"),headers ,HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable("categoryId") Integer categoryId){
        List<ProductDto>  products = productService.getAllProducts(categoryId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        return  new ResponseEntity<>(products,headers,HttpStatus.OK);
    }

    @GetMapping("/details/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Integer productId){
        ProductDto  product = productService.getProduct(productId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        return  new ResponseEntity<>(product,headers,HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto, @PathVariable("productId") Integer productId) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        if(!optionalCategory.isPresent())
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category does not exists"),headers ,HttpStatus.BAD_REQUEST);

        productService.updateProduct(productDto , productId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product created") ,headers,HttpStatus.CREATED);
    }
}
