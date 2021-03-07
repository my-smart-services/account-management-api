package com.mss.accountmanagementapi.user.update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.NoSuchElementFoundException;
import com.mss.accountmanagementapi.user.data.UserEntity;
import com.mss.accountmanagementapi.user.data.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateUserService {

    private final UserRepository updateUserRepository;
    private final ObjectMapper objectMapper;

    public UpdateUserBO updateUser(final String username, final UpdateUserBO user) {
        log.info(String.format("Update user %s with %s!", username, user));

        UserEntity userEntity = updateUserRepository.findByUsername(username)
                .orElseThrow(new NoSuchElementFoundException(String.format("User %s is unknown!", username)));

        if(Objects.nonNull(user.getUsername())){
            log.info(String.format("Change username %s to %s!", userEntity.getUsername(), user.getUsername()));
            userEntity.setUsername(user.getUsername());
        }

        if(Objects.nonNull(user.getEmail())){
            log.info(String.format("Change email of username %s from %s to %s!", userEntity.getUsername(), userEntity.getEmail(), user.getEmail()));
            userEntity.setEmail(user.getEmail());
        }

        if(Objects.nonNull(user.getFirstname())){
            log.info(String.format("Change firstname of username %s from %s to %s!", userEntity.getUsername(), userEntity.getFirstname(), user.getFirstname()));
            userEntity.setFirstname(user.getFirstname());
        }

        if(Objects.nonNull(user.getLastname())){
            log.info(String.format("Change lastname of username %s from %s to %s!", userEntity.getUsername(), userEntity.getLastname(), user.getLastname()));
            userEntity.setLastname(user.getLastname());
        }

        UserEntity result = updateUserRepository.save(userEntity);
        return objectMapper.convertValue(result, UpdateUserBO.class);
    }

}
