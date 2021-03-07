package com.mss.accountmanagementapi.user.create;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.BadRequestException;
import com.mss.accountmanagementapi.common.error.ElementAlreadyPresentException;
import com.mss.accountmanagementapi.user.data.UserRepository;
import com.mss.accountmanagementapi.user.data.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CreateUserServiceTest {

    private final static ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private CreateUserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void prep(){
        userService = new CreateUserService(userRepository, MAPPER);
    }

    //****************************************************************************************************************//
    // createUser tests
    //****************************************************************************************************************//

    @Test
    void createUser_entryNotPresent_wellFormatted_test() {
        // arrange
        CreateUserBO createUserBO = CreateUserBO.builder()
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        CreateUserBO maxMust = userService.createUser(createUserBO);
        Optional<UserEntity> reference = userRepository.findById(maxMust.getId());

        // assert
        assertThat(maxMust).isEqualTo(createUserBO);
        assertThat(reference).isPresent();
    }

    @Test
    @Sql("/sql/genericTestData.sql")
    void createUser_entryPresent_wellFormatted_test() {
        // arrange
        CreateUserBO createUserBO = CreateUserBO.builder()
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        ElementAlreadyPresentException exception = catchThrowableOfType(() ->userService.createUser(createUserBO), ElementAlreadyPresentException.class);

        // assert
        assertThat(exception).isInstanceOf(ElementAlreadyPresentException.class)
                .hasMessage("422 UNPROCESSABLE_ENTITY \"An entity with common username or email already exist!\"");
    }

    @Test
    @Sql("/sql/genericTestData.sql")
    void createUser_emailPresent_wellFormatted_test() {
        // arrange
        CreateUserBO createUserBO = CreateUserBO.builder()
                .withUsername("NotPresent")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        ElementAlreadyPresentException exception = catchThrowableOfType(() ->userService.createUser(createUserBO), ElementAlreadyPresentException.class);

        // assert
        assertThat(exception).isInstanceOf(ElementAlreadyPresentException.class)
                .hasMessage("422 UNPROCESSABLE_ENTITY \"An entity with common username or email already exist!\"");
    }

    @Test
    @Sql("/sql/genericTestData.sql")
    void createUser_usernamePresent_wellFormatted_test() {
        // arrange
        CreateUserBO createUserBO = CreateUserBO.builder()
                .withUsername("MaxMust")
                .withEmail("NotPresent@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        ElementAlreadyPresentException exception = catchThrowableOfType(() ->userService.createUser(createUserBO), ElementAlreadyPresentException.class);

        // assert
        assertThat(exception).isInstanceOf(ElementAlreadyPresentException.class)
                .hasMessage("422 UNPROCESSABLE_ENTITY \"An entity with common username or email already exist!\"");
    }

    @Test
    void createUser_badFormatted_idPresent_test() {
        // arrange
        CreateUserBO createUserBO = CreateUserBO.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.createUser(createUserBO), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input CreateUserBO{id=1, username='MaxMust', firstname='Max', lastname='Mustermann', email='max.mustermann@example.org'}!\"");
    }

    @Test
    void createUser_badFormatted_emailNotPresent_test() {
        // arrange
        CreateUserBO createUserBO = new CreateUserBO();
        createUserBO.setUsername("MaxMust");
        createUserBO.setFirstname("Max");
        createUserBO.setLastname("Mustermann");

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.createUser(createUserBO), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input CreateUserBO{id=null, username='MaxMust', firstname='Max', lastname='Mustermann', email='null'}!\"");
    }

    @Test
    void createUser_badFormatted_usernameNotPresent_test() {
        // arrange
        CreateUserBO createUserBO = new CreateUserBO();
        createUserBO.setEmail("max.mustermann@example.org");
        createUserBO.setFirstname("Max");
        createUserBO.setLastname("Mustermann");

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.createUser(createUserBO), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input CreateUserBO{id=null, username='null', firstname='Max', lastname='Mustermann', email='max.mustermann@example.org'}!\"");
    }

    @Test
    void createUser_badFormatted_null_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userService.createUser(null), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input null!\"");
    }
}
