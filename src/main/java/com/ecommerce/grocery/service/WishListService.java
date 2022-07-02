package com.ecommerce.grocery.service;

import com.ecommerce.grocery.dto.ProductDto;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.model.WishList;
import com.ecommerce.grocery.repository.WishListRepository;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository ;
    @Autowired
    ProductService productService ;


    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWhishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();

        for(WishList wishList : wishLists)
            productDtos.add(productService.getProductDto(wishList.getProduct()));

        return productDtos;
    }
}
