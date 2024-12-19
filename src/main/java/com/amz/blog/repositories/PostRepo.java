package com.amz.blog.repositories;

import com.amz.blog.entities.Category;
import com.amz.blog.entities.Post;
import com.amz.blog.entities.User;
import com.amz.blog.payloads.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,String> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String keyword);

}
