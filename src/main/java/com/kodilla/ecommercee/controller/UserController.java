package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UserDto;
import com.kodilla.ecommercee.exceptions.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserDbService userDbService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userDbService.saveUser(userMapper.mapToUser(userDto));
        return userMapper.mapToUserDto(user);
    }

    @PutMapping("/{id}/block")
    public UserDto block(@PathVariable Long id) throws UserNotFoundException {
        return userMapper.mapToUserDto(userDbService.blockUser(id).orElseThrow(UserNotFoundException::new));
    }

    @PostMapping("/{id}/generateKey")
    public Long generateKey(@PathVariable Long id) throws UserNotFoundException {
        return userDbService.generateKey(id).orElseThrow(UserNotFoundException::new);
    }
}
