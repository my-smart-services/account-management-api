package com.mss.accountmanagementapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.error.BadRequestException;
import com.mss.accountmanagementapi.error.NoSuchElementFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
@Validated
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{username}")
    public UserDTO readUser(@PathVariable @NotEmpty @Size(min = 2, max = 32) String username) {
        return userService.findUser(username)
                .map(this::convert)
                .orElseThrow(new NoSuchElementFoundException("No user with username " + username + " found!"));
    }

    @GetMapping
    public List<UserDTO> readAllUser() {
        return userService.findAllUsers().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.registerNewUser(convert(userDTO));
        return convert(user);
    }

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO updateUser(@PathVariable @NotEmpty @Size(min = 2, max = 32) String username, @Valid @RequestBody UserDTO userDTO) {
        if (!username.equals(userDTO.getUsername())) {
            throw new BadRequestException("Username from Body didnt match resource username!");
        }

        User user = userService.updateUser(convert(userDTO));
        return convert(user);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO deleteUser(@NotEmpty @Size(min = 2, max = 32) @PathVariable String username) {
        User user = userService.deleteUser(username);
        return convert(user);
    }

    //****************************************************************************************************************//
    // validation
    //****************************************************************************************************************//

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationExceptions(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for(ConstraintViolation<?> error: ex.getConstraintViolations()) {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    public UserDTO convert(User user) {
        return objectMapper.convertValue(user, UserDTO.class);
    }

    public User convert(UserDTO user) {
        return objectMapper.convertValue(user, User.class);
    }

}
