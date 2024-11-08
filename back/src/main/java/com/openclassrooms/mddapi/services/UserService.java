package com.openclassrooms.mddapi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save a new user in Database
     *
     * @param user - The User to save mapped as UserLoginDTO
     *
     */
    public void registerNewUser(UserLoginDTO user) {
        User newUser = new User();

        // Convert UserDTO to User
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        modelMapper.map(userRepository.save(newUser), UserDTO.class);
    }

    /**
     * Get the current connected user of the session
     *
     * @param authentication - The current session properties
     * @return a User mapped as UserDTO
     *
     */
    public UserDTO getCurrentUser(Authentication authentication) {
        UserDTO currentUser = new UserDTO();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Convert User to UserDTO
        currentUser.setId(user.getId());
        currentUser.setEmail(user.getEmail());
        currentUser.setUsername(user.getUsername());
        currentUser.setCreated_at(user.getCreated_at());
        currentUser.setUpdated_at(user.getUpdated_at());
        return currentUser;
    }

    /**
     * Get a specific user in Database
     *
     * @param id - The identification of the User to get
     * @return a User mapped as UserDTO
     *
     */
    public UserDTO getUserById(final Long id) {
        return modelMapper.map(userRepository.findById(id), UserDTO.class);
    }
}
