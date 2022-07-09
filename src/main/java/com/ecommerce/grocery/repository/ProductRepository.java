package com.ecommerce.grocery.repository;

import com.ecommerce.grocery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Integer> {

    List<Product> findAllByCategoryId(Integer categoryId);
}
