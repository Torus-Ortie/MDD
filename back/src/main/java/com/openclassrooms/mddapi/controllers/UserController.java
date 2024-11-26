package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String emailOrName = authentication.getName();
        UserDTO userDTO = userService.getCurrentUser(emailOrName);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        String emailOrName = authentication.getName();
        return userService.updateUser(userDTO, emailOrName);
    }

    @PostMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO subscribeToTheme(@PathVariable Long themeId, Authentication authentication) {
        String emailOrName = authentication.getName();
        return userService.subscribeToTheme(themeId, emailOrName);
    }

    @DeleteMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO unsubscribeFromTheme(@PathVariable Long themeId, Authentication authentication) {
        String emailOrName = authentication.getName();
        return userService.unsubscribeFromTheme(themeId, emailOrName);
    }
}
