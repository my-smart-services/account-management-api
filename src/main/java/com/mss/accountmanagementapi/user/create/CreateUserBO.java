package com.mss.accountmanagementapi.user.create;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CreateUserBO {

    private Long id;
    // system information's
    @NonNull
    private String username;

    // Personal information's
    private String firstname;
    private String lastname;

    @NonNull
    private String email;

    //****************************************************************************************************************//
    // Contracts
    //****************************************************************************************************************//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateUserBO)) return false;
        CreateUserBO createUserBO = (CreateUserBO) o;
        return Objects.equals(getUsername(), createUserBO.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "CreateUserBO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
