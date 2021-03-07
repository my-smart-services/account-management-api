package com.mss.accountmanagementapi.user.delete;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class DeleteUserBO {

    private Long id;
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
        if (!(o instanceof DeleteUserBO)) return false;
        DeleteUserBO createUserBO = (DeleteUserBO) o;
        return Objects.equals(getUsername(), createUserBO.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "DeleteUserBO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
