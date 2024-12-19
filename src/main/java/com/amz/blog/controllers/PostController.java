package com.amz.blog.controllers;


import com.amz.blog.config.AppConstants;
import com.amz.blog.payloads.ApiResponse;
import com.amz.blog.payloads.PostDto;
import com.amz.blog.payloads.PostResponse;
import com.amz.blog.services.FileService;
import com.amz.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;


    @PostMapping("/user/{uid}/category/{catId}/posts")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto,
                                        @PathVariable String uid,@PathVariable String catId){
        PostDto createdPost = postService.createPost(postDto, uid, catId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/posts/{pid}")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto,@PathVariable String pid){
        PostDto updatedPost = this.postService.updatePost(postDto, pid);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
    //get by user
    @GetMapping("/posts/user/{uid}")
    public ResponseEntity<?> getPostByUser(@PathVariable String uid){
        List<PostDto> postsByUser = postService.getPostsByUser(uid);
        return new ResponseEntity<>(postsByUser,HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{catId}/posts")
    public ResponseEntity<?> getPostsByCategory(@PathVariable String catId){
        List<PostDto> postByCategory = this.postService.getPostByCategory(catId);
        return new ResponseEntity<>(postByCategory,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                         @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
                                         ){
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize,sortBy);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts/{pid}")
    public ResponseEntity<?> getPostById(@PathVariable String pid){
        PostDto post = postService.getPostById(pid);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{pid}")
    public ApiResponse deletePost(@PathVariable String pid){
        this.postService.deletePost(pid);
        return new ApiResponse("Post deleted successfully",true);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<?> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> postDtos = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @PostMapping("/posts/image/upload/{pid}")
    public ResponseEntity<?> uploadPostImage(@RequestParam("image")MultipartFile image,
                                             @PathVariable String pid) throws IOException {
        PostDto postDto = this.postService.getPostById(pid);

        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto, pid);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

    }
    //method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
