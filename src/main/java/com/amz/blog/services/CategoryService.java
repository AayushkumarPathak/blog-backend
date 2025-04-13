package com.amz.blog.services;

import java.util.List;

import com.amz.blog.payloads.CategoryDto;

public interface CategoryService {
    //CREATE
    CategoryDto createCategory(CategoryDto categoryDto);

    //UPDATE
    CategoryDto updateCategory(CategoryDto categoryDto,String id);

    //DELETE
    boolean deleteCategory(String id);


    //READ ONE
    CategoryDto getCategory(String id);

    //READ ALL
    List<CategoryDto> getAllCategories();


}
