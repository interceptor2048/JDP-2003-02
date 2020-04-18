package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserDbService {
    @Autowired
    UserRepository userRepository;

    Random generator = new Random(500000L);

    @Autowired
    public UserDbService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public Optional<User> blockUser(final Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setStatus("blocked");
            userRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    public Optional<Long> generateKey(final Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Long userKey = generator.nextLong();
            user.get().setUserKey(userKey);
            userRepository.save(user.get());
            return Optional.of(userKey);
        }
        return Optional.empty();
    }

    public Optional<User> getActualUser(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            return foundUser;
        }
        return Optional.empty();
    }
}
