package com.mss.accountmanagementapi.user.data;

import com.mss.accountmanagementapi.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class UserEntityTest {

    //****************************************************************************************************************//
    // equals test
    //****************************************************************************************************************//

    @Test
    void equals_equalIdents_test() {
        // arrange
        UserEntity userA = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserEntity userB = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMusthaus")
                .withFirstname("Maxi")
                .withLastname("Musterhaus")
                .withEmail("Max.Musterhaus@example.org")
                .build();

        // act
        boolean result = userA.equals(userB);

        //assert
        assertThat(result).isTrue();
    }

    @Test
    void equals_differentIdents_test() {
        // arrange
        UserEntity userA = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserEntity userB = UserEntity.builder()
                .withId(2L)
                .withUsername("MaxMusthaus")
                .withFirstname("Max")
                .withLastname("Musterhaus")
                .withEmail("Max.Musterhaus@example.org")
                .build();

        // act
        boolean result = userA.equals(userB);

        //assert
        assertThat(result).isFalse();
    }

    @Test
    void equals_comparableNull_test() {
        // arrange
        UserEntity userA = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        boolean result = userA.equals(null);

        //assert
        assertThat(result).isFalse();
    }

    @Test
    void equals_differentComparableClass_test() {
        // arrange
        UserEntity userA = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        Object userB = "notAUser";

        // act
        boolean result = userA.equals(userB);

        //assert
        assertThat(result).isFalse();
    }

    //****************************************************************************************************************//
    // hashCode test
    //****************************************************************************************************************//

    @Test
    void hashCode_equals_test() {
        // arrange
        UserEntity userA = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserEntity userB = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMusthaus")
                .withFirstname("Maxi")
                .withLastname("Musterhaus")
                .withEmail("Max.Musterhaus@example.org")
                .build();

        // act
        int userAHashCode = userA.hashCode();
        int userBHashCode = userB.hashCode();

        //assert
        assertThat(userAHashCode).isEqualTo(userBHashCode);
    }

    @Test
    void hashCode_differentIdent_test() {
        // arrange
        UserEntity userA = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserEntity userB = UserEntity.builder()
                .withId(2L)
                .withUsername("MaxMusthaus")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        int userAHashCode = userA.hashCode();
        int userBHashCode = userB.hashCode();

        //assert
        assertThat(userAHashCode).isNotEqualTo(userBHashCode);
    }

    //****************************************************************************************************************//
    // toString test
    //****************************************************************************************************************//

    @Test
    void toString_allFields_test() {
        // arrange
        UserEntity user = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        String output = user.toString();

        //assert
        assertThat(output).isEqualTo("UserEntity{id=1, username='MaxMust', firstname='Max', lastname='Mustermann', email='Max.Mustermann@example.org'}");
    }

    @Test
    void toString_mandatoryFields_test() {
        // arrange
        UserEntity user = UserEntity.builder()
                .withUsername("MaxMust")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        String output = user.toString();

        //assert
        assertThat(output).isEqualTo("UserEntity{id=null, username='MaxMust', firstname='null', lastname='null', email='Max.Mustermann@example.org'}");
    }

    //****************************************************************************************************************//
    // builder test
    //****************************************************************************************************************//

    @Test
    void builder_mandatoryGiven_test() {
        // arrange
        UserEntity.UserEntityBuilder userBuilder = UserEntity.builder()
                .withUsername("MaxMust")
                .withEmail("Max.Mustermann@example.org");

        // act
        UserEntity result = userBuilder.build();

        //assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("username", "MaxMust")
                .hasFieldOrPropertyWithValue("email", "Max.Mustermann@example.org")
                .hasFieldOrPropertyWithValue("firstname", null)
                .hasFieldOrPropertyWithValue("lastname", null);
    }

    @Test
    void builder_allGiven_test() {
        // arrange
        UserEntity.UserEntityBuilder userBuilder = UserEntity.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org");

        // act
        UserEntity result = userBuilder.build();

        //assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("username", "MaxMust")
                .hasFieldOrPropertyWithValue("email", "Max.Mustermann@example.org")
                .hasFieldOrPropertyWithValue("firstname", "Max")
                .hasFieldOrPropertyWithValue("lastname", "Mustermann");
    }


    //****************************************************************************************************************//
    // setter test
    //****************************************************************************************************************//

    @Test
    void setter_emailPresent_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setEmail("Max.Mustermann@example.org");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("email", "Max.Mustermann@example.org");
    }

    @Test
    void setter_emailNull_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setEmail(null);

        //assert
        assertThat(user)
                .hasFieldOrPropertyWithValue("email", null);
    }

    @Test
    void setter_usernamePresent_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setUsername("MaxMust");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("username", "MaxMust");
    }

    @Test
    void setter_usernameNull_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setUsername(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("username", null);
    }

    @Test
    void setter_firstnamePresent_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setFirstname("Max");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("firstname", "Max");
    }

    @Test
    void setter_firstnameNull_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setFirstname(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("firstname", null);
    }

    @Test
    void setter_lastnamePresent_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setLastname("Mustermann");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("lastname", "Mustermann");
    }

    @Test
    void setter_lastnameNull_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setLastname(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("lastname", null);
    }

    @Test
    void setter_idPresent_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setId(1L);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    void setter_idNull_test() {
        // arrange
        UserEntity user = new UserEntity();

        // act
        user.setId(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("id", null);
    }

}
