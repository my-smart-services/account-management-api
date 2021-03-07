package com.mss.accountmanagementapi.user.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
@Validated
public class CreateUserController {

    private final CreateUserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        CreateUserBO createUserBO = userService.createUser(convert(createUserDTO));
        return convert(createUserBO);
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    public CreateUserDTO convert(CreateUserBO user) {
        return objectMapper.convertValue(user, CreateUserDTO.class);
    }

    public CreateUserBO convert(CreateUserDTO user) {
        return objectMapper.convertValue(user, CreateUserBO.class);
    }

}
