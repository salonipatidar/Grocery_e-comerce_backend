package com.ecommerce.grocery.service;

import com.ecommerce.grocery.dto.cart.AddToCartDto;
import com.ecommerce.grocery.dto.cart.CartDto;
import com.ecommerce.grocery.dto.cart.CartItemDto;
import com.ecommerce.grocery.exceptions.CustomException;
import com.ecommerce.grocery.model.Cart;
import com.ecommerce.grocery.model.Product;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.repository.CartReposiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartReposiory cartReposiory;

    public Integer addToCart(AddToCartDto addToCartDto, User user) {
        //validate product id is valid or not
       Product product =  productService.findById(addToCartDto.getProductId());
        // save to cart
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        Cart cart1 = cartReposiory.save(cart);

        return cart1.getId();
    }

    public CartDto listCartItems(User user) {
    List<Cart> cartList = cartReposiory.findAllByUserOrderByCreatedDateDesc(user);

    List<CartItemDto> cartItemDtos = new ArrayList<>();
    double totalCost = 0 ;

    for(Cart cart : cartList){
        CartItemDto cartItemDto = new CartItemDto(cart);
        totalCost += cart.getQuantity() * cart.getProduct().getPrice();
        cartItemDtos.add(cartItemDto);

    }

    CartDto cartDto = new CartDto();
    cartDto.setCartItemDtos(cartItemDtos);
    cartDto.setTotalCost(totalCost);

    return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {

        // check item id belong to user
        Optional<Cart> optionalCart = cartReposiory.findById(cartItemId);

        if (!optionalCart.isPresent()) {
            throw new CustomException("cartItemId not valid");
        }

        // next check if the cartItem belongs to the user else throw CartItemNotExistException exception

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException("cart item does not belong to user");
        }

        cartReposiory.deleteById(cartItemId);
    }

    public List<Cart> getCartForUser(User user) {
        List<Cart> cartList = cartReposiory.findAllByUserOrderByCreatedDateDesc(user);
        return cartList;
    }

    public void updateCartItem(AddToCartDto cartDto, User user,Product product){
        Cart cart = cartReposiory.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartReposiory.save(cart);
    }

    @Transactional
    public void deleteAllItemsByUser(User user) {
        cartReposiory.deleteAllByUser(user);
    }
}
