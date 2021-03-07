package com.mss.accountmanagementapi.user.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.BadRequestException;
import com.mss.accountmanagementapi.common.error.NoSuchElementFoundException;
import com.mss.accountmanagementapi.user.data.UserEntity;
import com.mss.accountmanagementapi.user.data.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteUserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    /**
     * Deletes a user permanently from the system.
     * @param username of the target user
     * @throws BadRequestException if the username is invalid
     * @throws NoSuchElementFoundException if the user is unknown
     * @return the deleted user
     */
    public DeleteUserBO deleteUser(final String username) {
        log.info(String.format("Try to delete user with username %s!", username));

        if (isEmpty(username)) {
            log.error("Username is undefined!");
            throw new BadRequestException("Username is undefined!");
        }

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(new NoSuchElementFoundException("User is unknown!"));

        userRepository.deleteById(userEntity.getId());
        log.info(String.format("Deleted entity %s!", userEntity));

        return convert(userEntity);
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    private DeleteUserBO convert(UserEntity user) {
        return objectMapper.convertValue(user, DeleteUserBO.class);
    }

}
