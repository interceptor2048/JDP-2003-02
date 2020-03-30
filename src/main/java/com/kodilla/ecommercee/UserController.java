package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.UserDto;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @RequestMapping(method = RequestMethod.POST, value = "createUser", consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) {
    }

    @RequestMapping(method = RequestMethod.PUT, value = "blockUser")
    public void blockUser(@RequestParam Long Id) {
    }

    @RequestMapping(method = RequestMethod.POST, value = "generateKey")
    public void generateKey(@RequestParam Long Id) {
    }

}