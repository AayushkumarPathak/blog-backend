package com.amz.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;

    @NotEmpty
    @Size(min = 3,message = "Name must be greater than 3 char")
    private String name;

    @Email(message = "Email id is not valid")
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6,max = 18,message = "Password must be greater than 6 char")
    private String password;

    @NotEmpty
    @Size(min = 3,message = "About min 3 char required")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

    @JsonIgnore
    public String getPassword(){
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password){
        this.password = password;
    }
}
