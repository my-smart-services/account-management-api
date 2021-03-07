package com.mss.accountmanagementapi.user.delete;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.BadRequestException;
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
class DeleteUserServiceTest {

    private final static ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private DeleteUserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void prep(){
        userService = new DeleteUserService(MAPPER, userRepository);
    }

    //****************************************************************************************************************//
    // deleteUser tests
    //****************************************************************************************************************//

    @Test
    @Sql("/sql/genericTestData.sql")
    void deleteUser_success_test() {
        // arrange
        DeleteUserBO userBO = DeleteUserBO.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.musterhaus@example.org")
                .withFirstname("Maximilian")
                .withLastname("Musterhaus")
                .build();

        // act
        DeleteUserBO deletedUser = userService.deleteUser("MaxMust");

        // assert
        assertThat(deletedUser).isEqualTo(userBO);
        assertThat(userRepository.findByUsername("MaxMust")).isEmpty();
    }

    @Test
    void deleteUser_notPresent_test() {
        // arrange

        // act
        NoSuchElementFoundException exception = catchThrowableOfType(() -> userService.deleteUser("MaxMust"), NoSuchElementFoundException.class);

        // assert
        assertThat(exception).isInstanceOf(NoSuchElementFoundException.class)
                .hasMessage("404 NOT_FOUND \"User is unknown!\"");
    }

    @Test
    void deleteUser_emptyInput_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.deleteUser(""), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }

    @Test
    void deleteUser_nullInput_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.deleteUser(null), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }
}
