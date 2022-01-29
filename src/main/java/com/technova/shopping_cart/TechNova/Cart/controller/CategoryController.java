package com.technova.shopping_cart.TechNova.Cart.controller;

import com.technova.shopping_cart.TechNova.Cart.dto.CategoryRequest;
import com.technova.shopping_cart.TechNova.Cart.model.Category;
import com.technova.shopping_cart.TechNova.Cart.service.CategoryService;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/API")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/str1")
    public String category(){
        return"books";
    }

    /*
    @GetMapping("/categories")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

     */
    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategory(@RequestParam(name = "catName", required = false) String catName) {
        if (catName != null) {
            Optional<Category> category = categoryService.getCategoryByCatName(catName);
            if (category.isPresent()) {
                log.info("category with category name{} fetched",category.get().getCatName());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), "Category with catName:" + catName + "fetched successfully", category, null);
            }
            log.info("category with category name{} not found",category.get().getCatName());
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "Category with " + catName + "not found in our database", null, "Category not found");

        } else {
            List<Category> categories = categoryService.getAllCategory();
            if (categories.size() > 0) {
                log.info("{} categories available", categories.size());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), categories.size() + "categories available", categories, null);
            }
            log.info("{} categories available", categories.size());
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "No category available", null, "Category not found");


        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getCategoryById (@PathVariable Long catId){
        Optional<Category> existingCategory = categoryService.getCategoryById(catId);
        if (existingCategory.isPresent()) {
            log.info("category with {} id fetched",existingCategory.get().getCatId());
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "Category fetch successfully", existingCategory, null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "Category with id " + catId + " not found in our database", null, "Category not found");
    }
    /*
    @PostMapping("/categories")
    public Category createCategory(@RequestBody CategoryRequest categoryRequest){
        Category savedCategory=categoryService.createCategory(categoryRequest);
        return savedCategory;
    }

     */
    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory (@RequestBody CategoryRequest categoryRequest){
        Optional<Category> category = categoryService.getCategoryByCatName(categoryRequest.getCatName());
        if (category.isPresent()){
            log.info("category with category name{} already exits",category.get().getCatName());
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "Category already exist with same name "+categoryRequest.getCatName(),null,null);
        }


        Category savedCategory = categoryService.createCategory(categoryRequest);
        log.info("category with category name{} created successfully",category.get().getCatName());
        return ApiResponse.generateResponse(HttpStatus.OK.value(), "Category created successfully", savedCategory, null);
    }
    @PutMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory (@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        Optional<Category> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            categoryService.updateCategory(existingCategory.get(), categoryRequest);
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "Category updated", existingCategory.get(), null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "Sorry Category with id " + id + " not found",null, "category not found");
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteCategory (@PathVariable Long id){
        Optional<Category> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            categoryService.deleteCategory(id);
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "Category deleted successfully with id " + id, null, null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "Category with id " + id + " not found in our database", null, "Category not found");

    }

}

