package com.ecommerce.grocery.repository;

import com.ecommerce.grocery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product , Integer> {
}
