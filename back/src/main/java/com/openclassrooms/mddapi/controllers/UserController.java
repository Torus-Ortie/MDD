package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
}
