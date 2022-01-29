package com.technova.shopping_cart.TechNova.Cart.service;

import com.technova.shopping_cart.TechNova.Cart.dto.CategoryRequest;
import com.technova.shopping_cart.TechNova.Cart.dto.UserRequest;
import com.technova.shopping_cart.TechNova.Cart.model.Category;
import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    public List<Category> getAllCategory;
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest categoryRequest) {
        Category newCategory = new Category();
        newCategory.setCatName(categoryRequest.getCatName());
        newCategory.setCreatedBy(categoryRequest.getCreatedBy());
        return categoryRepository.save(newCategory);
    }
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByCatName(String catName) {
        return categoryRepository.findByCatName(catName);
    }
    public Category updateCategory(Category category, CategoryRequest categoryRequest) {

        category.setCatName(categoryRequest.getCatName());
        category.setCreatedBy(categoryRequest.getCreatedBy());
        return categoryRepository.save(category);
    }
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
