package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;

import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String emailOrName = authentication.getName();
        User user = userService.getCurrentUser(emailOrName);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        String emailOrName = authentication.getName();
        return modelMapper.map(userService.updateUser(modelMapper.map(userDTO, User.class), emailOrName), UserDTO.class);
    }

    @PostMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO subscribeToTheme(@PathVariable Long themeId, Authentication authentication) {
        String emailOrName = authentication.getName();
        return modelMapper.map(userService.subscribeToTheme(themeId, emailOrName), UserDTO.class);
    }

    @DeleteMapping("/themes/{themeId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO unsubscribeFromTheme(@PathVariable Long themeId, Authentication authentication) {
        String emailOrName = authentication.getName();
        return modelMapper.map(userService.unsubscribeFromTheme(themeId, emailOrName), UserDTO.class);
    }
}
