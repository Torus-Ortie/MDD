package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTO {
private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long articleId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
