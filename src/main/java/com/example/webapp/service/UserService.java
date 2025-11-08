package com.example.webapp.service;

import com.example.webapp.model.User;
import com.example.webapp.repository.InMemoryUserRepository;
import com.example.webapp.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserService {
    
    private final UserRepository userRepository;
    private final InMemoryUserRepository inMemoryUserRepository;
    
    @Autowired
    public UserService(UserRepository userRepository,
                      @Autowired(required = false) InMemoryUserRepository inMemoryUserRepository) {
        this.userRepository = userRepository;
        this.inMemoryUserRepository = inMemoryUserRepository;
    }
    
    public User createUser(User user) {
        log.info("Creating user: {}", user.getName());
        if (inMemoryUserRepository != null) {
            return inMemoryUserRepository.save(user);
        }
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        if (inMemoryUserRepository != null) {
            return inMemoryUserRepository.findAll();
        }
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        if (inMemoryUserRepository != null) {
            return inMemoryUserRepository.findById(id);
        }
        return userRepository.findById(id);
    }
    
    public Optional<User> updateUser(Long id, User updatedUser) {
        log.info("Updating user with id: {}", id);
        if (inMemoryUserRepository != null) {
            return inMemoryUserRepository.findById(id)
                    .map(existingUser -> {
                        existingUser.setName(updatedUser.getName());
                        existingUser.setEmail(updatedUser.getEmail());
                        existingUser.setAge(updatedUser.getAge());
                        return inMemoryUserRepository.save(existingUser);
                    });
        }
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setAge(updatedUser.getAge());
                    return userRepository.save(existingUser);
                });
    }
    
    public boolean deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        if (inMemoryUserRepository != null) {
            if (inMemoryUserRepository.existsById(id)) {
                inMemoryUserRepository.deleteById(id);
                return true;
            }
            return false;
        }
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
