package com.mss.accountmanagementapi.user.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.BadRequestException;
import com.mss.accountmanagementapi.user.data.UserEntity;
import com.mss.accountmanagementapi.user.data.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadUserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    /**
     * Find a specific {@link ReadUserBO} by username
     * @param username of the user
     * @throws BadRequestException if the username is invalid
     * @return an {@link Optional} {@link ReadUserBO}
     */
    public Optional<ReadUserBO> findUserByUsername(final String username) {
        log.info(String.format("Try to find user with username %s!", username));

        if (isEmpty(username)) {
            log.error("Username is undefined!");
            throw new BadRequestException("Username is undefined!");
        }

        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        optionalUser.ifPresent(user -> log.info("Found " + user.toString()));
        return optionalUser.map(this::convert);
    }

    /**
     * Find all {@link ReadUserBO}
     * @return all {@link ReadUserBO}
     */
    public List<ReadUserBO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    private ReadUserBO convert(UserEntity user) {
        return objectMapper.convertValue(user, ReadUserBO.class);
    }

}
