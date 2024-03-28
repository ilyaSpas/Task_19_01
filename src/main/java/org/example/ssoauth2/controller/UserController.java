package org.example.ssoauth2.controller;

import org.example.ssoauth2.dto.UserDto;
import org.example.ssoauth2.entity.User;
import org.example.ssoauth2.service.UserService;
import org.example.ssoauth2.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final JWTUtils jwtUtils;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(JWTUtils jwtUtils, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/new")
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto){
        System.out.println(jwtUtils.generateToken(userDto.getUsername()));
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody UserDto userDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
        }
        return jwtUtils.generateToken(userDto.getUsername());
    }
}
