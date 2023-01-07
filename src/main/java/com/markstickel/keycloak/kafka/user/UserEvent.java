package com.markstickel.keycloak.kafka.user;

import org.keycloak.models.UserModel;

/**
 * @author mstickel
 */
public class UserEvent {

    private String id;

    private UserModel model;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getModel() {
        return model;
    }

    public void setModel(UserModel model) {
        this.model = model;
    }
}
