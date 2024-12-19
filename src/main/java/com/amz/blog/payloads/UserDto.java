package com.amz.blog.payloads;

import com.amz.blog.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;

    @NotEmpty
    @Size(min = 3,message = "Name must be greater than 3")
    private String name;

    @Email(message = "Email id is not valid")
    private String email;

    @NotEmpty
    @Size(min = 6,max = 18,message = "Password must be greater than 6 char")
    private String password;

    @NotEmpty
    @Size(min = 3,message = "About min 3 char")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
