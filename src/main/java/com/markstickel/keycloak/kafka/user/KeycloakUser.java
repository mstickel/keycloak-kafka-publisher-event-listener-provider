package com.markstickel.keycloak.kafka.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author mstickel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakUser {

    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", email='" + email + '\'' +
               ", username='" + username + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               '}';
    }
}
