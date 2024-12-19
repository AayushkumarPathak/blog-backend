package com.amz.blog.repositories;

import com.amz.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,String> {

}
