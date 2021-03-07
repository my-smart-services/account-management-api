package com.mss.accountmanagementapi.user.update;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
@Validated
public class UpdateUserController {

    private final UpdateUserService userService;
    private final ObjectMapper objectMapper;

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UpdateUserDTO updateUser(@PathVariable @NotEmpty @Size(min = 2, max = 32) String username, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UpdateUserBO user = userService.updateUser(username, convert(updateUserDTO));
        return convert(user);
    }

    //****************************************************************************************************************//
    // helper
    //****************************************************************************************************************//

    public UpdateUserDTO convert(UpdateUserBO user) {
        return objectMapper.convertValue(user, UpdateUserDTO.class);
    }

    public UpdateUserBO convert(UpdateUserDTO user) {
        return objectMapper.convertValue(user, UpdateUserBO.class);
    }

}
