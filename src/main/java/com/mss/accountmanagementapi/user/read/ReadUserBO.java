package com.mss.accountmanagementapi.user.read;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class ReadUserBO {

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
        if (!(o instanceof ReadUserBO)) return false;
        ReadUserBO user = (ReadUserBO) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "ReadUserBO{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
