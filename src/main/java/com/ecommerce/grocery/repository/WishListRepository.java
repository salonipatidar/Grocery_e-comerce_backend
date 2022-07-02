package com.ecommerce.grocery.repository;

import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList , Integer> {

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
