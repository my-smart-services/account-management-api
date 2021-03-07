package com.mss.accountmanagementapi.user.read;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class ReadUserDTO {

    @Size(min=2, max=32)
    private String username;

    @Size(min=2,max=32)
    private String firstname;

    @Size(min=2,max=32)
    private String lastname;

    @Email
    private String email;


    //****************************************************************************************************************//
    // Contracts
    //****************************************************************************************************************//


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReadUserDTO)) return false;
        ReadUserDTO userDTO = (ReadUserDTO) o;
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
        return "ReadUserDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
