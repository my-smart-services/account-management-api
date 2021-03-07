package com.mss.accountmanagementapi.user.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
@Validated
public class DeleteUserController {

    private final DeleteUserService userService;
    private final ObjectMapper objectMapper;

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeleteUserDTO deleteUser(@NotEmpty @Size(min = 2, max = 32) @PathVariable String username) {
        DeleteUserBO createUserBO = userService.deleteUser(username);
        return convert(createUserBO);
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    public DeleteUserDTO convert(DeleteUserBO user) {
        return objectMapper.convertValue(user, DeleteUserDTO.class);
    }

}
