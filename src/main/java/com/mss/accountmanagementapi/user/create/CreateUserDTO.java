package com.mss.accountmanagementapi.user.create;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CreateUserDTO {

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
        if (!(o instanceof CreateUserDTO)) return false;
        CreateUserDTO createUserDTO = (CreateUserDTO) o;
        return Objects.equals(getUsername(), createUserDTO.getUsername())
                && Objects.equals(getFirstname(), createUserDTO.getFirstname())
                && Objects.equals(getLastname(), createUserDTO.getLastname())
                && Objects.equals(getEmail(), createUserDTO.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getFirstname(), getLastname(), getEmail());
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
