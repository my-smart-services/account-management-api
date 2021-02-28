package com.mss.accountmanagementapi.user.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.error.BadRequestException;
import com.mss.accountmanagementapi.error.ElementAlreadyPresentException;
import com.mss.accountmanagementapi.error.NoSuchElementFoundException;
import com.mss.accountmanagementapi.error.UnsupportedElementOperationException;
import com.mss.accountmanagementapi.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserResourceServiceTest {

    @InjectMocks
    private UserResourceService userResourceService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ObjectMapper objectMapper;

    //****************************************************************************************************************//
    // findByUsername tests
    //****************************************************************************************************************//

    @Test
    void findByUsername_isPresent_Test() {
        // arrange
        UserEntity userEntity = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();
        User user = objectMapper.convertValue(userEntity, User.class);

        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.ofNullable(userEntity));

        // act
        Optional<User> maxMust = userResourceService.findByUsername("MaxMust");

        // assert
        assertThat(maxMust).isNotEmpty();
        assertThat(maxMust.get()).isEqualTo(user);
    }

    @Test
    void findByUsername_isNotPresent_Test() {
        // arrange
        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.empty());

        // act
        Optional<User> maxMust = userResourceService.findByUsername("MaxMust");

        // assert
        assertThat(maxMust).isEmpty();
    }

    @Test
    void findByUsername_isEmpty_Test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.findByUsername(""), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }

    @Test
    void findByUsername_isNull_Test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.findByUsername(null), BadRequestException.class);

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
        UserEntity userEntity1 = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();
        User user1 = objectMapper.convertValue(userEntity1, User.class);
        UserEntity userEntity2 = UserEntity.builder()
                .withId(2L)
                .withUsername("MaxMustHaus")
                .withEmail("max.musterhaus@example.org")
                .withFirstname("Max")
                .withLastname("Musterhaus")
                .build();
        User user2 = objectMapper.convertValue(userEntity2, User.class);

        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity1, userEntity2));

        // act
        List<User> users = userResourceService.findAllUsers();

        // assert
        assertThat(users).isNotEmpty()
                .containsExactly(user1, user2);
    }

    @Test
    void findAllUsers_noEntriesPresent_test() {
        // arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // act
        List<User> users = userResourceService.findAllUsers();

        // assert
        assertThat(users).isEmpty();
    }

    //****************************************************************************************************************//
    // createUser tests
    //****************************************************************************************************************//

    @Test
    void createUser_entryNotPresent_wellFormatted_test() {
        // arrange
        User user = User.builder()
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        UserEntity userEntityResult = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        when(userRepository.save(eq(objectMapper.convertValue(user, UserEntity.class)))).thenReturn(userEntityResult);
        when(userRepository.existsByUsernameOrEmail(eq("MaxMust"), eq("max.mustermann@example.org"))).thenReturn(false);

        // act
        User maxMust = userResourceService.createUser(user);

        // assert
        assertThat(maxMust).isEqualTo(user)
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    void createUser_entryPresent_wellFormatted_test() {
        // arrange
        User user = User.builder()
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        when(userRepository.existsByUsernameOrEmail(eq("MaxMust"), eq("max.mustermann@example.org"))).thenReturn(true);

        // act
        ElementAlreadyPresentException exception = catchThrowableOfType(() ->userResourceService.createUser(user), ElementAlreadyPresentException.class);

        // assert
        assertThat(exception).isInstanceOf(ElementAlreadyPresentException.class)
                .hasMessage("422 UNPROCESSABLE_ENTITY \"An entity with common username or email already exist!\"");
    }

    @Test
    void createUser_badFormatted_idPresent_test() {
        // arrange
        User user = User.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.createUser(user), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input User{id=1, username='MaxMust', firstname='Max', lastname='Mustermann', email='max.mustermann@example.org'}!\"");
    }

    @Test
    void createUser_badFormatted_emailNotPresent_test() {
        // arrange
        User user = new User();
        user.setUsername("MaxMust");
        user.setFirstname("Max");
        user.setLastname("Mustermann");

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.createUser(user), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input User{id=null, username='MaxMust', firstname='Max', lastname='Mustermann', email='null'}!\"");
    }

    @Test
    void createUser_badFormatted_usernameNotPresent_test() {
        // arrange
        User user = new User();
        user.setEmail("max.mustermann@example.org");
        user.setFirstname("Max");
        user.setLastname("Mustermann");

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.createUser(user), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input User{id=null, username='null', firstname='Max', lastname='Mustermann', email='max.mustermann@example.org'}!\"");
    }

    @Test
    void createUser_badFormatted_null_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.createUser(null), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Invalid input null!\"");
    }

    //****************************************************************************************************************//
    // updateUser tests
    //****************************************************************************************************************//

    @Test
    void updateUser_success_test() {
        // arrange
        User user = User.builder()
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Maximilian")
                .withLastname("Musterhaus")
                .build();

        UserEntity updatedUserEntity = objectMapper.convertValue(user, UserEntity.class);
        updatedUserEntity.setId(1L);

        UserEntity userEntityResult = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.ofNullable(userEntityResult));
        when(userRepository.save(eq(updatedUserEntity))).thenReturn(updatedUserEntity);

        // act
        User maxMust = userResourceService.updateUser(user);

        // assert
        assertThat(maxMust).isEqualTo(user)
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    void updateUser_emailChanged_test() {
        // arrange
        User user = User.builder()
                .withUsername("MaxMust")
                .withEmail("max.musterhaus@example.org")
                .withFirstname("Maximilian")
                .withLastname("Musterhaus")
                .build();

        UserEntity userEntityResult = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.mustermann@example.org")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .build();

        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.ofNullable(userEntityResult));

        // act
        UnsupportedElementOperationException exception = catchThrowableOfType(() -> userResourceService.updateUser(user), UnsupportedElementOperationException.class);

        // assert
        assertThat(exception).isInstanceOf(UnsupportedElementOperationException.class)
                .hasMessage("510 NOT_EXTENDED \"Email change is not supported! Please dont use this method to change the email address!\"");
    }

    @Test
    void updateUser_unknownUser_test() {
        // arrange
        User user = User.builder()
                .withUsername("MaxMust")
                .withEmail("max.musterhaus@example.org")
                .withFirstname("Maximilian")
                .withLastname("Musterhaus")
                .build();

        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.empty());

        // act
        NoSuchElementFoundException exception = catchThrowableOfType(() -> userResourceService.updateUser(user), NoSuchElementFoundException.class);

        // assert
        assertThat(exception).isInstanceOf(NoSuchElementFoundException.class)
                .hasMessage("404 NOT_FOUND \"User is unknown!\"");
    }

    @Test
    void updateUser_invalidInput_usernameMissing_test() {
        // arrange
        User user = new User();
        user.setEmail("max.mustermann@example.org");
        user.setFirstname("Max");
        user.setLastname("Mustermann");

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.updateUser(user), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Incomplete information User{id=null, username='null', firstname='Max', lastname='Mustermann', email='max.mustermann@example.org'}!\"");
    }

    @Test
    void updateUser_invalidInput_emailMissing_test() {
        // arrange
        User user = new User();
        user.setUsername("MaxMust");
        user.setFirstname("Max");
        user.setLastname("Mustermann");

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.updateUser(user), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Incomplete information User{id=null, username='MaxMust', firstname='Max', lastname='Mustermann', email='null'}!\"");
    }

    @Test
    void updateUser_invalidInput_null_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.updateUser(null), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Incomplete information null!\"");
    }

    //****************************************************************************************************************//
    // deleteUser tests
    //****************************************************************************************************************//

    @Test
    void deleteUser_success_test() {
        // arrange
        UserEntity userEntity = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withEmail("max.musterhaus@example.org")
                .withFirstname("Maximilian")
                .withLastname("Musterhaus")
                .build();

        User user = objectMapper.convertValue(userEntity, User.class);

        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.ofNullable(userEntity));

        // act
        User deletedUser = userResourceService.deleteUser("MaxMust");

        // assert
        verify(userRepository, times(1)).deleteById(eq(1L));
        assertThat(deletedUser).isEqualTo(user);
    }

    @Test
    void deleteUser_notPresent_test() {
        // arrange
        when(userRepository.findByUsername(eq("MaxMust"))).thenReturn(Optional.empty());

        // act
        NoSuchElementFoundException exception = catchThrowableOfType(() -> userResourceService.deleteUser("MaxMust"), NoSuchElementFoundException.class);

        // assert
        assertThat(exception).isInstanceOf(NoSuchElementFoundException.class)
                .hasMessage("404 NOT_FOUND \"User is unknown!\"");
    }

    @Test
    void deleteUser_emptyInput_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.deleteUser(""), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }

    @Test
    void deleteUser_nullInput_test() {
        // arrange

        // act
        BadRequestException exception = catchThrowableOfType(() -> userResourceService.deleteUser(null), BadRequestException.class);

        // assert
        assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("400 BAD_REQUEST \"Username is undefined!\"");
    }

}
