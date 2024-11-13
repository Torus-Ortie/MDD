package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String emailOrName;
    private String password;
}
