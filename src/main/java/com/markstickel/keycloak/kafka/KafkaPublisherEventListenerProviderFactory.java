package com.markstickel.keycloak.kafka;

import com.markstickel.keycloak.kafka.user.UserEventPublisher;
import java.util.HashMap;
import java.util.Map;
import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

public class KafkaPublisherEventListenerProviderFactory implements EventListenerProviderFactory,
                                                                   ServerInfoAwareProviderFactory {

    private static final Logger logger = Logger.getLogger(KafkaPublisherEventListenerProviderFactory.class);

    private UserEventPublisher userEventPublisher;

    @Override
    public KafkaPublisherEventListenerProvider create(KeycloakSession session) {
        return new KafkaPublisherEventListenerProvider(userEventPublisher, session);
    }

    @Override
    public void init(Scope scope) {
        userEventPublisher = new UserEventPublisher();
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        logger.info("factory postInit");
    }

    @Override
    public void close() {
        userEventPublisher.close();
    }

    @Override
    public String getId() {
        return "kafka-event-publisher";
    }

    @Override
    public Map<String, String> getOperationalInfo() {
        return new HashMap<>();
    }
}