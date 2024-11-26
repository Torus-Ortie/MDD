package com.openclassrooms.mddapi.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegisterDTO;
import com.openclassrooms.mddapi.services.JWTService;
import com.openclassrooms.mddapi.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private JWTService jwtService;

	@Autowired
    private UserService userService;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
	public ResponseEntity<Map<String, String>> getRegistreted(@RequestBody UserRegisterDTO userRegisterDTO) {
		userService.registerNewUser(userRegisterDTO);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
		String emailOrName = authentication.getName();
        String token = jwtService.generateToken(emailOrName);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public ResponseEntity<Map<String, String>> getToken(@RequestBody UserLoginDTO userLoginDTO) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmailOrName(), userLoginDTO.getPassword());
		String emailOrName = authentication.getName();
		String token = jwtService.generateToken(emailOrName);
		return ResponseEntity.ok(Collections.singletonMap("token", token));
	}
}