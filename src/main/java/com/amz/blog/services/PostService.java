package com.amz.blog.services;

import java.util.List;

import com.amz.blog.payloads.PostDto;
import com.amz.blog.payloads.PostResponse;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto,String uid,String catId);

    //update
    PostDto updatePost(PostDto postDto,String postId);

    void deletePost(String postId);

    //get all post
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    //get single post
    PostDto getPostById(String id);

    // get posts by category
    List<PostDto> getPostByCategory(String catId);


    //get posts by user
    List<PostDto> getPostsByUser(String uid);

    //search post
    List<PostDto> searchPosts(String keyword);


}
