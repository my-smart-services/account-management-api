package com.mss.accountmanagementapi.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class UserTest {

    //****************************************************************************************************************//
    // equals test
    //****************************************************************************************************************//

    @Test
    void equals_equalIdents_test() {
        // arrange
        User userA = User.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        User userB = User.builder()
                .withId(2L)
                .withUsername("MaxMust")
                .withFirstname("Max")
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
        User userA = User.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        User userB = User.builder()
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
        User userA = User.builder()
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
        User userA = User.builder()
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
        User userA = User.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        User userB = User.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
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
        User userA = User.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        User userB = User.builder()
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
        User user = User.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        String output = user.toString();

        //assert
        assertThat(output).isEqualTo("User{id=1, username='MaxMust', firstname='Max', lastname='Mustermann', email='Max.Mustermann@example.org'}");
    }

    @Test
    void toString_mandatoryFields_test() {
        // arrange
        User user = User.builder()
                .withUsername("MaxMust")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        String output = user.toString();

        //assert
        assertThat(output).isEqualTo("User{id=null, username='MaxMust', firstname='null', lastname='null', email='Max.Mustermann@example.org'}");
    }

    //****************************************************************************************************************//
    // builder test
    //****************************************************************************************************************//

    @Test
    void builder_mandatoryGiven_test() {
        // arrange
        User.UserBuilder userBuilder = User.builder()
                .withUsername("MaxMust")
                .withEmail("Max.Mustermann@example.org");

        // act
        User result = userBuilder.build();

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
        User.UserBuilder userBuilder = User.builder()
                .withId(1L)
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org");

        // act
        User result = userBuilder.build();

        //assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("username", "MaxMust")
                .hasFieldOrPropertyWithValue("email", "Max.Mustermann@example.org")
                .hasFieldOrPropertyWithValue("firstname", "Max")
                .hasFieldOrPropertyWithValue("lastname", "Mustermann");
    }

    @Test
    void builder_noUsernameGiven_test() {
        // arrange
        User.UserBuilder userBuilder = User.builder()
                .withEmail("Max.Mustermann@example.org");

        // act
        NullPointerException result = catchThrowableOfType(userBuilder::build, NullPointerException.class);

        //assert
        assertThat(result).isNotNull()
                .hasMessage("username is marked non-null but is null");
    }

    @Test
    void builder_noEmailGiven_test() {
        // arrange
        User.UserBuilder userBuilder = User.builder()
                .withUsername("MaxMust");

        // act
        NullPointerException result = catchThrowableOfType(userBuilder::build, NullPointerException.class);

        //assert
        assertThat(result).isNotNull()
                .hasMessage("email is marked non-null but is null");
    }

    //****************************************************************************************************************//
    // setter test
    //****************************************************************************************************************//

    @Test
    void setter_emailPresent_test() {
        // arrange
        User user = new User();

        // act
        user.setEmail("Max.Mustermann@example.org");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("email", "Max.Mustermann@example.org");
    }

    @Test
    void setter_emailNull_test() {
        // arrange
        User user = new User();

        // act
        NullPointerException result = catchThrowableOfType(() -> user.setEmail(null), NullPointerException.class);

        //assert
        assertThat(result).isNotNull()
                .hasMessage("email is marked non-null but is null");
    }

    @Test
    void setter_usernamePresent_test() {
        // arrange
        User user = new User();

        // act
        user.setUsername("MaxMust");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("username", "MaxMust");
    }

    @Test
    void setter_usernameNull_test() {
        // arrange
        User user = new User();

        // act
        NullPointerException result = catchThrowableOfType(() -> user.setUsername(null), NullPointerException.class);

        //assert
        assertThat(result).isNotNull()
                .hasMessage("username is marked non-null but is null");
    }

    @Test
    void setter_firstnamePresent_test() {
        // arrange
        User user = new User();

        // act
        user.setFirstname("Max");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("firstname", "Max");
    }

    @Test
    void setter_firstnameNull_test() {
        // arrange
        User user = new User();

        // act
        user.setFirstname(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("firstname", null);
    }

    @Test
    void setter_lastnamePresent_test() {
        // arrange
        User user = new User();

        // act
        user.setLastname("Mustermann");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("lastname", "Mustermann");
    }

    @Test
    void setter_lastnameNull_test() {
        // arrange
        User user = new User();

        // act
        user.setLastname(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("lastname", null);
    }

    @Test
    void setter_idPresent_test() {
        // arrange
        User user = new User();

        // act
        user.setId(1L);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    void setter_idNull_test() {
        // arrange
        User user = new User();

        // act
        user.setId(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("id", null);
    }

}
