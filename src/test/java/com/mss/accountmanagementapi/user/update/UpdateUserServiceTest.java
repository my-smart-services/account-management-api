package com.mss.accountmanagementapi.user.update;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.NoSuchElementFoundException;
import com.mss.accountmanagementapi.user.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/sql/genericTestData.sql")
class UpdateUserServiceTest {

    private UpdateUserService userService;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @BeforeEach
    public void prep() {
        userService = new UpdateUserService(userRepository, MAPPER);
    }

    //****************************************************************************************************************//
    // updateUser tests
    //****************************************************************************************************************//

    @Test
    void updateUser_success_test() {
        // arrange
        UpdateUserBO updateUserBO = UpdateUserBO.builder()
                .withUsername("NewUsername")
                .withEmail("new.email@example.org")
                .withFirstname("NewFirstname")
                .withLastname("NewLastname")
                .build();

        // User MaxMust already exists with different data

        // act
        UpdateUserBO maxMust = userService.updateUser("MaxMust", updateUserBO);

        // assert
        assertThat(maxMust).isEqualTo(updateUserBO);
        assertThat(userRepository.findByUsername("MaxMust")).isEmpty();
    }

    @Test
    void updateUser_unknownUser_test() {
        // arrange
        UpdateUserBO updateUserBO = UpdateUserBO.builder()
                .withUsername("MaxMust")
                .withEmail("max.musterhaus@example.org")
                .withFirstname("Maximilian")
                .withLastname("Musterhaus")
                .build();

        // act
        NoSuchElementFoundException exception = catchThrowableOfType(() -> userService.updateUser("NotPresent", updateUserBO), NoSuchElementFoundException.class);

        // assert
        assertThat(exception).isInstanceOf(NoSuchElementFoundException.class)
                .hasMessage("404 NOT_FOUND \"User NotPresent is unknown!\"");
    }

}
