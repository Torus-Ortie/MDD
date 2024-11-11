package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<Long> subscribedThemeIds;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
