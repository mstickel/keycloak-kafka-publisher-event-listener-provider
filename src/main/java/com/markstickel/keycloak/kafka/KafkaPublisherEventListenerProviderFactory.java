package com.markstickel.keycloak.kafka;

import com.markstickel.keycloak.kafka.user.UserEventPublisher;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

public class KafkaPublisherEventListenerProviderFactory implements EventListenerProviderFactory,
                                                                   ServerInfoAwareProviderFactory {

    private static final Logger logger = Logger.getLogger(KafkaPublisherEventListenerProviderFactory.class);
    private static final String KAFKA_TOPIC = "KAFKA_TOPIC";
    private static final String KAFKA_BOOTSTRAP_SERVER = "KAFKA_BOOTSTRAP_SERVER";

    private UserEventPublisher userEventPublisher;

    private String kafkaBootstrapUrl;
    private String topic;

    @Override
    public KafkaPublisherEventListenerProvider create(KeycloakSession session) {
        return new KafkaPublisherEventListenerProvider(userEventPublisher, session);
    }

    @Override
    public void init(Scope scope) {
        this.topic = Optional.ofNullable(System.getenv(KAFKA_TOPIC)).orElse("abcd");
        this.kafkaBootstrapUrl = Optional.ofNullable(System.getenv(KAFKA_BOOTSTRAP_SERVER)).orElse("popfizzclink:9092");
        logger.info("Initiating Kafka publisher. Bootstrap URL is " + kafkaBootstrapUrl + "; topic to publish on is " + topic);
        userEventPublisher = new UserEventPublisher(kafkaBootstrapUrl, topic);
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
        Map<String, String> opInfo = new HashMap<>();
        opInfo.put("kafkaBootstrapUrl", this.kafkaBootstrapUrl);
        opInfo.put("topic", this.topic);
        return opInfo;
    }
}