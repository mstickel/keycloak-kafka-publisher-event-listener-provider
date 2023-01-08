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

As an example, this is how one might do it in a Dockerfile:

```
COPY keycloak-kafka-publisher-event-listener-provider-1.0.0-SNAPSHOT.jar /opt/bitnami/keycloak/providers/keycloak-kafka-publisher-event-listener-provider-1.0.0-SNAPSHOT.jar
COPY kafka-clients-3.3.1.jar /opt/bitnami/keycloak/providers/kafka-clients-3.3.1.jar
```

For more information on how to configure custom Keycloak providers, see the Keycloak documentation here: https://www.keycloak.org/server/configuration-provider

### Configure It

This provider requires the following settings, which must be passed as environment variables to Keycloak:
* `KAFKA_TOPIC` - the name of the Kafka topic to publish Keycloak events to
* `KAFKA_BOOTSTRAP_URL` - the Kafka `bootstrap-server` url.  Example: `localhost:9092`

Kafka settings are passed to Keycloak as environment variables instead of as command line args to `kc.sh` due to limitations with the Bitnami Keycloak Helm chart and the passing of dynamic urls (specifically, the kafka bootstrap url).