package com.amz.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amz.blog.entities.Category;
import com.amz.blog.entities.Post;
import com.amz.blog.entities.User;
import com.amz.blog.exceptions.ResourceNotFoundException;
import com.amz.blog.payloads.PostDto;
import com.amz.blog.payloads.PostResponse;
import com.amz.blog.repositories.CategoryRepo;
import com.amz.blog.repositories.PostRepo;
import com.amz.blog.repositories.UserRepo;
import com.amz.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,String uid,String catId) {

        User user = this.userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", uid));

        Category category = categoryRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", catId));

        Post thisPost = modelMapper.map(postDto, Post.class);

        //set the required to be filled not done by API
        thisPost.setPostId(UUID.randomUUID().toString());
        thisPost.setImageName("deafult.png");
        thisPost.setDateCreated(new Date());
        thisPost.setUser(user);
        thisPost.setCategory(category);

        Post savedPost = postRepo.save(thisPost);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, String postId) {
        Post oldpost = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "pid", postId));
        Post newPost = modelMapper.map(postDto, Post.class);

        oldpost.setTitle(newPost.getTitle());
        oldpost.setContent(newPost.getContent());
        oldpost.setCategory(newPost.getCategory());

        oldpost.setUser(newPost.getUser());
        oldpost.setImageName(newPost.getImageName());
        Post updatedPost = postRepo.save(oldpost);

        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(String postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "Postid", postId));
        postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePosts = postRepo.findAll(p);
        List<Post> posts = pagePosts.getContent();


        List<PostDto> postDtos = posts.stream()
                .map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(String id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", id));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(String catId) {
        Category category = categoryRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", catId));
        List<Post> posts = postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(String uid) {
        User user = userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "uid", uid));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
