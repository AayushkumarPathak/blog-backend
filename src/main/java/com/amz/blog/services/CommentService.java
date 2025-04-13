package com.amz.blog.services;

import com.amz.blog.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto comment,String postId);

    void deleteComment(String cid);
}
