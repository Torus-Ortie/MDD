package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @NonNull
    Optional<User> findById(@NonNull Long id);
    
    User findByEmail(String email);
    User findByName(String name);
}
