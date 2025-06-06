package com.amz.blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amz.blog.payloads.ApiResponse;
import com.amz.blog.payloads.CategoryDto;
import com.amz.blog.services.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //get
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id){
        CategoryDto category = categoryService.getCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    //create
    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto catDto){
        catDto.setId(UUID.randomUUID().toString());
        CategoryDto category = categoryService.createCategory(catDto);
        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable String id){
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(String id){
        boolean isDeleted = categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted",true),HttpStatus.OK);
    }

    //getAll
    @GetMapping("/")
    public ResponseEntity<?> getAllCategories(){
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }
}
