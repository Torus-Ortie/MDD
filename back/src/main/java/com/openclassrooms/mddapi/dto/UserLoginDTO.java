package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String username;
    private String email;
    private String password;
}
