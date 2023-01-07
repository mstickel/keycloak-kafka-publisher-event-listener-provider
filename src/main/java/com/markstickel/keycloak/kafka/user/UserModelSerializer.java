package com.markstickel.keycloak.kafka.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.serialization.Serializer;
import org.keycloak.models.UserModel;

/**
 * @author mstickel
 */
public class UserModelSerializer implements Serializer<UserModel> {

    private final ObjectMapper objectMapper;

    public UserModelSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, UserModel userModel) {
        KeycloakUser keycloakUser = new KeycloakUser();
        keycloakUser.setEmail(userModel.getEmail());
        keycloakUser.setId(userModel.getId());
        keycloakUser.setFirstName(userModel.getFirstName());
        keycloakUser.setLastName(userModel.getLastName());
        keycloakUser.setUsername(userModel.getUsername());
        try {
            return objectMapper.writeValueAsString(keycloakUser).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
