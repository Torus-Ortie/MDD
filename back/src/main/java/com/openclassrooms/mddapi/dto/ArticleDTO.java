package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private String themeTitle;
    private Long themeId;
    private List<Long> commentIds;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
