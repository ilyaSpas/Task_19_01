package org.example.ssoauth2.service;

import org.example.ssoauth2.dto.UserDto;
import org.example.ssoauth2.entity.User;

public interface UserService {

    User save(UserDto user);
}
