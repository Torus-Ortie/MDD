package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String name;
    private String email;
    private String password;
}
