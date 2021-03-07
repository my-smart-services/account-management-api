package com.mss.accountmanagementapi.user.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.BadRequestException;
import com.mss.accountmanagementapi.common.error.ElementAlreadyPresentException;
import com.mss.accountmanagementapi.user.data.UserRepository;
import com.mss.accountmanagementapi.user.data.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    /**
     * Creates a new user
     *
     * @throws BadRequestException if the input is invalid
     * @throws ElementAlreadyPresentException if the user already exists
     * @param createUserBO the user which should be created
     * @return the created user
     */
    public CreateUserBO createUser(CreateUserBO createUserBO) {
        if (isNull(createUserBO) || isEmpty(createUserBO.getUsername()) || isEmpty(createUserBO.getEmail()) || nonNull(createUserBO.getId())) {
            log.error(String.format("Invalid input %s!", createUserBO));
            throw new BadRequestException(String.format("Invalid input %s!", createUserBO));
        }

        if (userRepository.existsByUsernameOrEmail(createUserBO.getUsername(), createUserBO.getEmail())) {
            log.error("An entity with common username {} or email {} already exist!", createUserBO.getUsername(), createUserBO.getEmail());
            throw new ElementAlreadyPresentException("An entity with common username or email already exist!");
        }

        UserEntity userEntity = userRepository.save(convert(createUserBO));
        log.info(String.format("Created entity %s!", userEntity));
        return convert(userEntity);
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    private UserEntity convert(CreateUserBO createUserBO) {
        return objectMapper.convertValue(createUserBO, UserEntity.class);
    }

    private CreateUserBO convert(UserEntity user) {
        return objectMapper.convertValue(user, CreateUserBO.class);
    }

}
