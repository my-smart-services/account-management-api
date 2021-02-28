package com.mss.accountmanagementapi.user;

import com.mss.accountmanagementapi.user.data.UserResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserResourceService userResourceService;

    public List<User> findAllUsers(){
        return userResourceService.findAllUsers();
    }

    public Optional<User> findUser(final String username) {
        log.info(String.format("Try to find user with username %s!", username));
        return userResourceService.findByUsername(username);
    }

    public User registerNewUser(final User user) {
        log.info(String.format("Register new %s!", user));
        User createdUser = userResourceService.createUser(user);
        log.info(String.format("Registration was successful! Assigned ID %s!", createdUser.getId()));
        return createdUser;
    }

    public User updateUser(final User user){
        log.info(String.format("Update user %s!", user));
        User updatedUser = userResourceService.updateUser(user);
        log.info("Update successful!");
        return updatedUser;
    }

    public User deleteUser(final String username) {
        log.info(String.format("Try to delete user with username %s!", username));
        User user = userResourceService.deleteUser(username);
        log.info(String.format("Deletion was successful! The following user got deleted %s!", user));
        return user;
    }

}
