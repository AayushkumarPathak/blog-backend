package com.amz.blog.repositories;

import com.amz.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,String> {
}
