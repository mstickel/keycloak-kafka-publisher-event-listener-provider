package com.markstickel.keycloak.kafka;

import com.markstickel.keycloak.kafka.user.UserEvent;
import com.markstickel.keycloak.kafka.user.UserEventPublisher;
import java.util.concurrent.ExecutionException;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;

public class KafkaPublisherEventListenerProvider
        implements EventListenerProvider {

    private static final Logger logger = Logger.getLogger(KafkaPublisherEventListenerProvider.class);

    private final UserEventPublisher kafkaPublisher;

    private final UserProvider userProvider;

    private final RealmModel realmModel;

    public KafkaPublisherEventListenerProvider(UserEventPublisher kafkaPublisher, KeycloakSession session) {
        this.kafkaPublisher = kafkaPublisher;
        this.userProvider = session.getProvider(UserProvider.class);
        this.realmModel = session.getContext().getRealm();
    }

    @Override
    public void onEvent(Event event) {
        if (EventType.REGISTER.equals(event.getType())) {
            UserModel userModel = userProvider.getUserById(realmModel, event.getUserId());
            logger.debug("A new user registered: " + userModel.getEmail());
            UserEvent userEvent = new UserEvent();
            userEvent.setId(event.getId());
            userEvent.setModel(userModel);
            try {
                kafkaPublisher.publishCreate(userEvent);
            } catch (ExecutionException | InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean b) {
    }

    @Override
    public void close() {
    }
}