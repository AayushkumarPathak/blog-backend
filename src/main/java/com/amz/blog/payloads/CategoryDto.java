package com.amz.blog.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class CategoryDto {

    private String id;

    @NotBlank
    @Size(min = 4)
    private String title;

    @NotBlank
    private String description;
}
