package com.markstickel.keycloak.kafka.user;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jboss.logging.Logger;
import org.keycloak.models.UserModel;

public class UserEventPublisher {

    private static final Logger logger = Logger.getLogger(UserEventPublisher.class);

    private final Producer producer;
    private final String topic;

    public UserEventPublisher(String kafkaBootstrapUrl, String topic) {
        this.producer = createProducer(kafkaBootstrapUrl);
        this.topic = topic;
    }

    public void publishCreate(UserEvent object) throws ExecutionException, InterruptedException {
        logger.warn("Publishing user event on Kafka: " + object.getModel());
        ProducerRecord<String, UserModel> record = new ProducerRecord<>(topic, object.getId(), object.getModel());
        producer.send(record).get();
        producer.flush();
        logger.warn("Event was published");
    }

    public void close() {
        producer.flush();
        producer.close();
    }

    private Producer<String, UserModel> createProducer(String kafkaBootstrapUrl) {
        Properties props = new Properties();
        logger.info("Setting property " + ProducerConfig.BOOTSTRAP_SERVERS_CONFIG + ": " + kafkaBootstrapUrl);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                  kafkaBootstrapUrl);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "UserProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                  StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                  UserModelSerializer.class.getName());
        KafkaProducer<String, UserModel> producer = new KafkaProducer<>(props);
        return producer;
    }

}