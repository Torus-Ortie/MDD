package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegisterDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save a new user in Database
     *
     * @param userRegisterDTO - The User to save mapped as UserLoginDTO
     * @return The User saved mapped as UserLoginDTO
     * 
     */
    public UserDTO registerNewUser(UserRegisterDTO userRegisterDTO) {
        User existingUser = userRepository.findByEmail(userRegisterDTO.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email is already in use");
        }

        existingUser = userRepository.findByName(userRegisterDTO.getName());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username is already in use");
        }

        if (userRegisterDTO.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setName(userRegisterDTO.getName());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    /**
     * Authenticate a user
     *
     * @param userloginDTO - The User to authenticate mapped as UserLoginDTO
     * @return The current session properties
     * 
     */
    public Authentication authenticate(UserLoginDTO userloginDTO) {
        User user = userRepository.findByEmail(userloginDTO.getEmailOrName());
        if (user == null) {
            user = userRepository.findByName(userloginDTO.getEmailOrName());
        }
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or username");
        }
        try {

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userloginDTO.getEmailOrName(), userloginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    /**
     * Get the current connected user of the session
     *
     * @param authentication - The current session properties
     * @return User mapped as UserDTO
     *
     */
    public UserDTO getCurrentUser(Authentication authentication) {
        String emailOrName = authentication.getName();
        User user = userRepository.findByEmail(emailOrName);
        if (user == null) {
            user = userRepository.findByName(emailOrName);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Update a user
     *
     * @param userDTO - The User to update mapped as UserLoginDTO
     * @param authentication - The current session properties
     * @return User updated and mapped as UserDTO
     * 
     */
    public UserDTO updateUser(UserDTO userDTO, Authentication authentication) {
        UserDTO currentUserDTO = getCurrentUser(authentication);
        User user = userRepository.findById(currentUserDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    /**
     * Subscribe a theme
     *
     * @param themeId - The theme to subscribe
     * @param authentication - The current session properties
     * @return User mapped as UserDTO
     * 
     */
    public UserDTO subscribeToTheme(Long themeId, Authentication authentication) {
        UserDTO currentUserDTO = getCurrentUser(authentication);
        User user = userRepository.findById(currentUserDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!themeRepository.existsById(themeId)) {
            throw new EntityNotFoundException("Theme not found");
        }
        if (user.getSubscribedThemeIds().contains(themeId)) {
            throw new IllegalArgumentException("User is already subscribed to this theme");
        }
        user.getSubscribedThemeIds().add(themeId);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    /**
     * Unsubscribe a theme
     *
     * @param themeId - The theme to unsibscribe
     * @param authentication - The current session properties
     * @return User mapped as UserDTO
     * 
     */
    public UserDTO unsubscribeFromTheme(Long themeId, Authentication authentication) {
        UserDTO currentUserDTO = getCurrentUser(authentication);
        User user = userRepository.findById(currentUserDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!themeRepository.existsById(themeId)) {
            throw new EntityNotFoundException("Theme not found");
        }
        if (!user.getSubscribedThemeIds().contains(themeId)) {
            throw new IllegalArgumentException("User is not subscribed to this theme");
        }
        user.getSubscribedThemeIds().remove(themeId);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}