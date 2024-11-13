package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.updateUser(userDTO, authentication);
    }

    @PostMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO subscribeToTheme(@PathVariable Long themeId, Authentication authentication) {
        return userService.subscribeToTheme(themeId, authentication);
    }

    @DeleteMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO unsubscribeFromTheme(@PathVariable Long themeId, Authentication authentication) {
        return userService.unsubscribeFromTheme(themeId, authentication);
    }
}
