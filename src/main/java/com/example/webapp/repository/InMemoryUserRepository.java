package com.example.webapp.repository;

import com.example.webapp.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("memory")
public class InMemoryUserRepository {
    
    private final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        userStore.put(user.getId(), user);
        return user;
    }
    
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userStore.get(id));
    }
    
    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }
    
    public void deleteById(Long id) {
        userStore.remove(id);
    }
    
    public boolean existsById(Long id) {
        return userStore.containsKey(id);
    }
    
    public Optional<User> findByEmail(String email) {
        return userStore.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
