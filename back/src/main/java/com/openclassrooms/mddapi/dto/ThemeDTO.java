package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ThemeDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
