package com.amz.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    private String id;
    private String name;
}
