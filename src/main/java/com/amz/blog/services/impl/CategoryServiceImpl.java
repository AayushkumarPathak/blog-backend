package com.amz.blog.services.impl;

import com.amz.blog.entities.Category;
import com.amz.blog.exceptions.ResourceNotFoundException;
import com.amz.blog.payloads.CategoryDto;
import com.amz.blog.repositories.CategoryRepo;
import com.amz.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = modelMapper.map(categoryDto, Category.class);

        Category savedCat = categoryRepo.save(cat);

        return modelMapper.map(savedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String id) {
        Category category = this.categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category saveCat = this.categoryRepo.save(category);

        return modelMapper.map(saveCat,CategoryDto.class);
    }

    @Override
    public boolean deleteCategory(String id) {
        try{
            Category category = categoryRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", id));
            categoryRepo.delete(category);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CategoryDto getCategory(String id) {
        Category category = this.categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", id));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
        return catDtos;
    }
}
