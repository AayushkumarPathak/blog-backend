package com.amz.blog.controllers;

import com.amz.blog.payloads.ApiResponse;
import com.amz.blog.payloads.CommentDto;
import com.amz.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto comment,@PathVariable String postId){
        CommentDto saved = this.commentService.createComment(comment, postId);
        return new ResponseEntity<>(saved,HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteComment(@PathVariable String cid){
        this.commentService.deleteComment(cid);
        return new ResponseEntity<>(
                new ApiResponse("Comment Deleted Successfully"
                        ,true)
                ,HttpStatus.OK);
    }


}
