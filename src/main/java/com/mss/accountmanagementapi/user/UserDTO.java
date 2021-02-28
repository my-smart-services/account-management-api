package com.mss.accountmanagementapi.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class UserDTO {

    // system information's
    @NotBlank
    @Size(min=2, max=32)
    private String username;

    // Personal information's
    @Size(min=2,max=32)
    private String firstname;

    @Size(min=2,max=32)
    private String lastname;

    @NotBlank
    @Email
    private String email;

    //****************************************************************************************************************//
    // Contracts
    //****************************************************************************************************************//


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getUsername(), userDTO.getUsername())
                && Objects.equals(getFirstname(), userDTO.getFirstname())
                && Objects.equals(getLastname(), userDTO.getLastname())
                && Objects.equals(getEmail(), userDTO.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getFirstname(), getLastname(), getEmail());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
