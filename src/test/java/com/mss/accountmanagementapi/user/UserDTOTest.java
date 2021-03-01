package com.mss.accountmanagementapi.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOTest {

    //****************************************************************************************************************//
    // equals test
    //****************************************************************************************************************//

    @Test
    void equals_equalIdents_test() {
        // arrange
        UserDTO userA = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserDTO userB = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        boolean result = userA.equals(userB);

        //assert
        assertThat(result).isTrue();
    }

    @Test
    void equals_differentIdents_test() {
        // arrange
        UserDTO userA = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserDTO userB = UserDTO.builder()
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
        UserDTO userA = UserDTO.builder()
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
        UserDTO userA = UserDTO.builder()
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
        UserDTO userA = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserDTO userB = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
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
        UserDTO userA = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        UserDTO userB = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Musterhaus")
                .withEmail("Max.Musterhaus@example.org")
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
        UserDTO user = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org")
                .build();

        // act
        String output = user.toString();

        //assert
        assertThat(output).isEqualTo("UserDTO{username='MaxMust', firstname='Max', lastname='Mustermann', email='Max.Mustermann@example.org'}");
    }

    @Test
    void toString_mandatoryFields_test() {
        // arrange
        UserDTO user = UserDTO.builder().build();

        // act
        String output = user.toString();

        //assert
        assertThat(output).isEqualTo("UserDTO{username='null', firstname='null', lastname='null', email='null'}");
    }

    //****************************************************************************************************************//
    // builder test
    //****************************************************************************************************************//

    @Test
    void builder_mandatoryGiven_test() {
        // arrange
        UserDTO.UserDTOBuilder userBuilder = UserDTO.builder();

        // act
        UserDTO result = userBuilder.build();

        //assert
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("username", null)
                .hasFieldOrPropertyWithValue("email", null)
                .hasFieldOrPropertyWithValue("firstname", null)
                .hasFieldOrPropertyWithValue("lastname", null);
    }

    @Test
    void builder_allGiven_test() {
        // arrange
        UserDTO.UserDTOBuilder userBuilder = UserDTO.builder()
                .withUsername("MaxMust")
                .withFirstname("Max")
                .withLastname("Mustermann")
                .withEmail("Max.Mustermann@example.org");

        // act
        UserDTO result = userBuilder.build();

        //assert
        assertThat(result).isNotNull()
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
        UserDTO user = new UserDTO();

        // act
        user.setEmail("Max.Mustermann@example.org");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("email", "Max.Mustermann@example.org");
    }

    @Test
    void setter_emailNull_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setEmail(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("email", null);
    }

    @Test
    void setter_usernamePresent_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setUsername("MaxMust");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("username", "MaxMust");
    }

    @Test
    void setter_usernameNull_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setUsername(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("username", null);
    }

    @Test
    void setter_firstnamePresent_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setFirstname("Max");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("firstname", "Max");
    }

    @Test
    void setter_firstnameNull_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setFirstname(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("firstname", null);
    }

    @Test
    void setter_lastnamePresent_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setLastname("Mustermann");

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("lastname", "Mustermann");
    }

    @Test
    void setter_lastnameNull_test() {
        // arrange
        UserDTO user = new UserDTO();

        // act
        user.setLastname(null);

        //assert
        assertThat(user).isNotNull()
                .hasFieldOrPropertyWithValue("lastname", null);
    }

}
