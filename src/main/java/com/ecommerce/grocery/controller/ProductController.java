package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.common.ApiResponse;
import com.ecommerce.grocery.dto.ProductDto;
import com.ecommerce.grocery.model.Category;
import com.ecommerce.grocery.repository.CategoryRepo;
import com.ecommerce.grocery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = {"http://localhost:3000" , "https://salonipatidar.github.io" , "https://arpit194.github.io"})
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

//    @ModelAttribute
//    public void setResponseHeader(HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category does not exists") ,HttpStatus.BAD_REQUEST);

        productService.createProduct(productDto , optionalCategory.get());
        return  new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product created"),HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable("categoryId") Integer categoryId){
        List<ProductDto>  products = productService.getAllProducts(categoryId);
        return  new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/details/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Integer productId){
        ProductDto  product = productService.getProduct(productId);
        return  new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto, @PathVariable("productId") Integer productId) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Category does not exists"),HttpStatus.BAD_REQUEST);

        productService.updateProduct(productDto , productId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product created") ,HttpStatus.CREATED);
    }
}
