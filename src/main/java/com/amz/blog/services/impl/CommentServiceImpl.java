package com.amz.blog.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amz.blog.entities.Comment;
import com.amz.blog.entities.Post;
import com.amz.blog.exceptions.ResourceNotFoundException;
import com.amz.blog.payloads.CommentDto;
import com.amz.blog.repositories.CommentRepo;
import com.amz.blog.repositories.PostRepo;
import com.amz.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;


    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto,String postId) {
        commentDto.setId(UUID.randomUUID().toString());
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        commentRepo.save(comment);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(String cid) {
        Comment comment = this.commentRepo.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Comment: ", "Id", cid));
        commentRepo.deleteById(cid);

    }
}
