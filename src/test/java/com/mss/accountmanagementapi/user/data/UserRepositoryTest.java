package com.mss.accountmanagementapi.user.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/sql/genericTestData.sql")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    //****************************************************************************************************************//
    // findByUsername tests
    //****************************************************************************************************************//

    @Test
    void findByUsername_userPresent_test() {
        // arrange
        UserEntity expectedEntity = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("max.mustermann@example.org")
                .build();

        // act
        Optional<UserEntity> foundUser = userRepository.findByUsername("MaxMust");

        // assert
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get()).isEqualTo(expectedEntity);
    }

    @Test
    void findByUsername_multipleUserPresent_test() {
        // arrange
        UserEntity expectedEntity = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("max.mustermann@example.org")
                .build();

        // act
        Optional<UserEntity> foundUser = userRepository.findByUsername("MaxMust");

        // assert
        assertThat(foundUser).isNotEmpty();
        assertThat(foundUser.get()).isEqualTo(expectedEntity);
    }

    @Test
    void findByUsername_userUnknown_test() {
        // arrange

        // act
        Optional<UserEntity> foundUser = userRepository.findByUsername("NotMaxMust");

        // assert
        assertThat(foundUser).isEmpty();
    }

    //****************************************************************************************************************//
    // existsByUsernameOrEmail tests
    //****************************************************************************************************************//

    @Test
    void existsByUsernameOrEmail_usernamePresent_test() {
        // arrange

        // act
        boolean result = userRepository.existsByUsernameOrEmail("MaxMust", "notTheEmail@example.org");

        // assert
        assertThat(result).isTrue();
    }

    @Test
    void existsByUsernameOrEmail_emailPresent_test() {
        // arrange

        // act
        boolean result = userRepository.existsByUsernameOrEmail("NotMaxMust", "max.mustermann@example.org");

        // assert
        assertThat(result).isTrue();
    }

    @Test
    void existsByUsernameOrEmail_bothPresent_test() {
        // arrange

        // act
        boolean result = userRepository.existsByUsernameOrEmail("MaxMust", "max.mustermann@example.org");

        // assert
        assertThat(result).isTrue();
    }

    @Test
    void existsByUsernameOrEmail_notPresent_test() {
        // arrange

        // act
        boolean result = userRepository.existsByUsernameOrEmail("NotMaxMust", "notTheEmail@example.org");

        // assert
        assertThat(result).isFalse();
    }
}
