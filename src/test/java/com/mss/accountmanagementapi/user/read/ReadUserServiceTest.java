package com.mss.accountmanagementapi.user.read;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.BadRequestException;
import com.mss.accountmanagementapi.user.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/sql/genericTestData.sql")
class ReadUserServiceTest {

    private final static ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ReadUserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void prep() {
        userService = new ReadUserService(userRepository, MAPPER);
    }

    //****************************************************************************************************************//
    // findUserByUsername tests
    //****************************************************************************************************************//

    @Test
    void findByUsername_isPresent_Test() {
        // arrange
        ReadUserBO userBO = ReadUserBO.builder()
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        Optional<ReadUserBO> maxMust = userService.findUserByUsername("MaxMust");

        // assert
        assertThat(maxMust).isPresent()
                .get()
                .isEqualTo(userBO);
    }

    @Test
    void findByUsername_isNotPresent_Test() {
        // arrange

        // act
        Optional<ReadUserBO> maxMust = userService.findUserByUsername("NotPresent");

        // assert
        assertThat(maxMust).isEmpty();
    }

    @Test
    void findByUsername_isEmpty_Test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.findUserByUsername(""), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }

    @Test
    void findByUsername_isNull_Test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.findUserByUsername(null), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }

    //****************************************************************************************************************//
    // findAllUsers tests
    //****************************************************************************************************************//

    @Test
    void findAllUsers_entriesPresent_test() {
        // arrange

        // act
        List<ReadUserBO> readUserBOS = userService.findAllUsers();

        // assert
        assertThat(readUserBOS).isNotEmpty();
    }

    @Test
    void findAllUsers_noEntriesPresent_test() {
        // arrange
        userRepository.deleteAll();

        // act
        List<ReadUserBO> readUserBOS = userService.findAllUsers();

        // assert
        assertThat(readUserBOS).isEmpty();
    }

}
