package com.mss.accountmanagementapi.user.delete;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class DeleteUserDTO {

    private String username;
    private String firstname;
    private String lastname;
    private String email;

    //****************************************************************************************************************//
    // Contracts
    //****************************************************************************************************************//


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteUserDTO)) return false;
        DeleteUserDTO createUserDTO = (DeleteUserDTO) o;
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
        return "DeleteUserDTO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
