package com.mss.accountmanagementapi.user;

import com.mss.accountmanagementapi.error.BadRequestException;
import com.mss.accountmanagementapi.user.data.UserResourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserResourceService userResourceService;

    //****************************************************************************************************************//
    // findAllUsers test
    //****************************************************************************************************************//

    @Test
    void findAllUsers_success_test() {
        // arrange
        User user1 = new User(1L, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");
        User user2 = new User(1L, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");

        when(userResourceService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // act
        List<User> allUsers = userService.findAllUsers();

        // assert
        assertThat(allUsers).containsExactly(user1, user2);
    }

    @Test
    void findAllUsers_empty_test() {
        // arrange
        when(userResourceService.findAllUsers()).thenReturn(new ArrayList<>());

        // act
        List<User> allUsers = userService.findAllUsers();

        // assert
        assertThat(allUsers).isEmpty();
    }

    //****************************************************************************************************************//
    // findUser test
    //****************************************************************************************************************//

    @Test
    void findUser_present_test() {
        // arrange
        User user = new User(1L, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");

        when(userResourceService.findByUsername(eq("MaxMust"))).thenReturn(Optional.of(user));

        // act
        Optional<User> optionalUser = userService.findUser("MaxMust");

        // assert
        assertThat(optionalUser).isPresent()
                .contains(user);
    }

    @Test
    void findUser_notPresent_test() {
        // arrange
        when(userResourceService.findByUsername(eq("MaxMust"))).thenReturn(Optional.empty());

        // act
        Optional<User> optionalUser = userService.findUser("MaxMust");

        // assert
        assertThat(optionalUser).isEmpty();
    }

    @Test
    void findUser_null_test() {
        // arrange
        when(userResourceService.findByUsername(eq(null))).thenThrow(new BadRequestException("Some Exception"));

        // act
        BadRequestException runtimeException = catchThrowableOfType(() -> userService.findUser(null), BadRequestException.class);

        // assert
        assertThat(runtimeException).isNotNull();
    }

    //****************************************************************************************************************//
    // registerNewUser test
    //****************************************************************************************************************//

    @Test
    void registerNewUser_success_test() {
        // arrange
        User user = new User(null, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");
        User createdUser = new User(1L, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");

        when(userResourceService.createUser(eq(user))).thenReturn(createdUser);

        // act
        User resultUser = userService.registerNewUser(user);

        // assert
        assertThat(resultUser).isEqualTo(createdUser);
    }

    @Test
    void registerNewUser_null_test() {
        // arrange
        when(userResourceService.createUser(eq(null))).thenThrow(new BadRequestException("Some Exception"));

        // act
        BadRequestException runtimeException = catchThrowableOfType(() -> userService.registerNewUser(null), BadRequestException.class);

        // assert
        assertThat(runtimeException).isNotNull();
    }

    //****************************************************************************************************************//
    // updateUser test
    //****************************************************************************************************************//

    @Test
    void updateUser_success_test() {
        // arrange
        User user = new User(1L, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");
        User createdUser = new User(1L, "MaxMust", "Maximilian", "Musterhaus", "max.mustermann@example.com");

        when(userResourceService.updateUser(eq(user))).thenReturn(createdUser);

        // act
        User resultUser = userService.updateUser(user);

        // assert
        assertThat(resultUser).isEqualTo(createdUser);
    }

    @Test
    void updateUser_null_test() {
        // arrange
        when(userResourceService.updateUser(eq(null))).thenThrow(new BadRequestException("Some Exception"));

        // act
        BadRequestException runtimeException = catchThrowableOfType(() -> userService.updateUser(null), BadRequestException.class);

        // assert
        assertThat(runtimeException).isNotNull();
    }

    //****************************************************************************************************************//
    // deleteUser test
    //****************************************************************************************************************//

    @Test
    void deleteUser_success_test() {
        // arrange
        User user = new User(1L, "MaxMust", "Max", "Mustermann", "max.mustermann@example.com");

        when(userResourceService.deleteUser("MaxMust")).thenReturn(user);

        // act
        User resultUser = userService.deleteUser("MaxMust");

        // assert
        assertThat(resultUser).isEqualTo(user);
    }

    @Test
    void deleteUser_null_test() {
        // arrange
        when(userResourceService.deleteUser(eq(null))).thenThrow(new BadRequestException("Some Exception"));

        // act
        BadRequestException runtimeException = catchThrowableOfType(() -> userService.deleteUser(null), BadRequestException.class);

        // assert
        assertThat(runtimeException).isNotNull();
    }
}
