package com.investly.investly.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.investly.investly.model.CreateUserDto;
import com.investly.investly.model.User;
import com.investly.investly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(CreateUserDto createUserDto) {
        var user = new User();

        BeanUtils.copyProperties(createUserDto, user);

        user.setPasswordHash(BCrypt.withDefaults().hashToString(12, createUserDto.getPassword().toCharArray()));

        return userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}