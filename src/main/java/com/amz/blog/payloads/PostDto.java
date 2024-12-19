package com.amz.blog.payloads;

import com.amz.blog.entities.Category;
import com.amz.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private String imageName;
    private Date dateCreated;

    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();

}
