package com.mss.accountmanagementapi.user.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.accountmanagementapi.common.error.NoSuchElementFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
@Validated
public class ReadUserController {

    private final ReadUserService readUserService;
    private final ObjectMapper objectMapper;

    @GetMapping("/{username}")
    public ReadUserDTO readUser(@PathVariable @NotEmpty @Size(min = 2, max = 32) String username) {
        return readUserService.findUserByUsername(username)
                .map(this::convert)
                .orElseThrow(new NoSuchElementFoundException("No user with username " + username + " found!"));
    }

    @GetMapping
    public List<ReadUserDTO> readAllUser() {
        return readUserService.findAllUsers().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    public ReadUserDTO convert(ReadUserBO user) {
        return objectMapper.convertValue(user, ReadUserDTO.class);
    }

}
