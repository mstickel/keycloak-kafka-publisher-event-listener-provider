# Keycloak Kafka Publisher Event Listener Provider

This Keycloak extension publishes an event on a Kafka topic whenever certain Keycloak events occur.

Events that are currently supported:
* A new user registers (`EventType.REGISTER`)

### Get The Code

Available on GitHub: `git clone https://github.com/mstickel/keycloak-kafka-publisher-event-listener-provider`

### Build It

After checking out the code, run `mvn clean install` to build.  The build artifact is a jar.

### Include It

This library is also available on Maven Central:

```
<dependency>
  <groupId>com.markstickel.keycloak</groupId>
  <artifactId>keycloak-kafka-publisher-event-listener-provider</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Plug It In to Keycloak

In order to use this provider in Keycloak, you'll need to position it and its Apache Kafka dependency in Keycloak's `providers` directory:

As an example, this is how we do it in a Dockerfile:

```
COPY keycloak-kafka-publisher-event-listener-provider-1.0.0-SNAPSHOT.jar /opt/bitnami/keycloak/providers/keycloak-kafka-publisher-event-listener-provider-1.0.0-SNAPSHOT.jar
COPY kafka-clients-3.3.1.jar /opt/bitnami/keycloak/providers/kafka-clients-3.3.1.jar
```

For more information on how to do this, see the Keycloak documentation here: https://www.keycloak.org/server/configuration-provider

### Configure It

This provider requires the following settings:
* `kafka-topic` - the name of the Kafka topic to publish Keycloak events to
* `kafka-url` - the Kafka `bootstrap-server` url.  Example: `localhost:9092`