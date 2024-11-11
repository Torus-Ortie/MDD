package com.openclassrooms.mddapi.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private JWTService jwtService;

	@Autowired
    private UserService userService;

	public LoginController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public ResponseEntity<Map<String, String>> getRegistreted(@RequestBody UserLoginDTO userLoginDTO) {
		userService.registerNewUser(userLoginDTO);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public ResponseEntity<Map<String, String>> getToken(@RequestBody UserLoginDTO userLoginDTO) {
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } 
	}

	@GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(userDTO);
    }
}