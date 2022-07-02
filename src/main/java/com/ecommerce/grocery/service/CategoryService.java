package com.ecommerce.grocery.service;

import com.ecommerce.grocery.model.Category;
import com.ecommerce.grocery.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo ;

    public void createCategory(Category category){
        categoryRepo.save(category);
    }

    public List<Category> listCategory(){
        return  categoryRepo.findAll();
    }

    public void updateCategory(Integer categoryID, Category newCategory) {
        Category category = categoryRepo.findById(categoryID).get();
        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        //category.setProducts(newCategory.getProducts());
        category.setImageUrl(newCategory.getImageUrl());

        categoryRepo.save(category);
    }

    public boolean findById(Integer categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepo.deleteById(categoryId);
    }
}
