package com.healthcare.healthcaremanagement.controller;

import com.healthcare.healthcaremanagement.dto.UserDto;
import com.healthcare.healthcaremanagement.entity.User;
import com.healthcare.healthcaremanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "User controller")
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get all users on database.
     *
     * @return 200 success
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all users on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Create user on database.
     *
     * @return 200 success
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create an users on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public User createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }
}
