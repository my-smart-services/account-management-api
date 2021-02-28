package com.mss.accountmanagementapi.user.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.error.BadRequestException;
import com.mss.accountmanagementapi.error.ElementAlreadyPresentException;
import com.mss.accountmanagementapi.error.NoSuchElementFoundException;
import com.mss.accountmanagementapi.error.UnsupportedElementOperationException;
import com.mss.accountmanagementapi.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserResourceService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    /**
     * This method can be used to search for an specific user.
     *
     * @throws BadRequestException if the username is empty
     * @param username name of the user
     * @return optional user (empty if not present)
     */
    public Optional<User> findByUsername(String username) {
        if (isEmpty(username)) {
            log.error("Username is undefined!");
            throw new BadRequestException("Username is undefined!");
        }

        log.info(String.format("Search for user with username %s!", username));
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        optionalUser.ifPresent(user -> log.info("Found " + user.toString()));
        return optionalUser.map(this::convert);
    }

    /**
     * Returns all users.
     * @return a list of all users
     */
    public List<User> findAllUsers() {
        log.info("Search all users!");
        return userRepository.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user
     *
     * @throws BadRequestException if the input is invalid
     * @throws ElementAlreadyPresentException if the user already exists
     * @param user the user which should be created
     * @return the created user
     */
    public User createUser(User user) {
        if (isNull(user) || isEmpty(user.getUsername()) || isEmpty(user.getEmail()) || nonNull(user.getId())) {
            log.error(String.format("Invalid input %s!", user));
            throw new BadRequestException(String.format("Invalid input %s!", user));
        }
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            log.error("An entity with common username {} or email {} already exist!", user.getUsername(), user.getEmail());
            throw new ElementAlreadyPresentException("An entity with common username or email already exist!");
        }

        UserEntity userEntity = userRepository.save(convert(user));
        log.info(String.format("Created entity %s!", userEntity));
        return convert(userEntity);
    }

    /**
     * Updates the user details.
     *
     * @throws BadRequestException if the input is incomplete or invalid
     * @throws NoSuchElementFoundException if the user is not present.
     * @throws UnsupportedElementOperationException if the update of the field is not allowed.
     * @param user the new user content
     * @return the updated user
     */
    public User updateUser(User user) {
        if (isNull(user) || isNull(user.getUsername()) || isNull(user.getEmail())) {
            log.error(String.format("Incomplete information %s!", user));
            throw new BadRequestException(String.format("Incomplete information %s!", user));
        }
        log.info(String.format("Try to perform update on user %s!", user.getUsername()));

        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(new NoSuchElementFoundException("User is unknown!"));

        if(!userEntity.getEmail().equals(user.getEmail())){
            log.error(String.format("Email change from %s to %s for user %s detected! Please dont use this method to change the email address!", userEntity.getEmail(), user.getEmail(), user.getUsername()));
            throw new UnsupportedElementOperationException("Email change is not supported! Please dont use this method to change the email address!");
        }

        userEntity.setUsername(user.getUsername());
        userEntity.setFirstname(user.getFirstname());

        return convert(userRepository.save(userEntity));
    }

    /**
     * Can be used to delete a user.
     *
     * @throws BadRequestException if the username is empty
     * @throws NoSuchElementFoundException if the user is unknown
     * @param username of the user
     * @return deleted entity
     */
    public User deleteUser(String username) {
        if (isEmpty(username)) {
            log.error("Username is undefined!");
            throw new BadRequestException("Username is undefined!");
        }

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(new NoSuchElementFoundException("User is unknown!"));
        userRepository.deleteById(userEntity.getId());
        log.info(String.format("Deleted entity %s!", userEntity.toString()));

        return convert(userEntity);
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    public UserEntity convert(User user) {
        return objectMapper.convertValue(user, UserEntity.class);
    }

    public User convert(UserEntity user) {
        return objectMapper.convertValue(user, User.class);
    }
}
