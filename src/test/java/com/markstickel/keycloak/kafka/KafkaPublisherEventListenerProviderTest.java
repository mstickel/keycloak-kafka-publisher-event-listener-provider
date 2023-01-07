package com.markstickel.keycloak.kafka;

import com.markstickel.keycloak.kafka.user.UserEvent;
import com.markstickel.keycloak.kafka.user.UserEventPublisher;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author mstickel
 */
@ExtendWith(MockitoExtension.class)
public class KafkaPublisherEventListenerProviderTest {

    @Mock
    private UserEventPublisher kafkaPublisher;

    @Mock
    private KeycloakSession session;

    @Mock
    private UserProvider userProvider;

    @Mock
    private RealmModel realm;

    private KafkaPublisherEventListenerProvider provider;

    @BeforeEach
    public void setUp() {
        when(session.getProvider(UserProvider.class)).thenReturn(userProvider);
        KeycloakContext context = mock(KeycloakContext.class);
        when(session.getContext()).thenReturn(context);
        when(context.getRealm()).thenReturn(realm);
        provider = new KafkaPublisherEventListenerProvider(kafkaPublisher, session);
    }

    @Test
    public void testOnEvent()
            throws ExecutionException, InterruptedException {
        String userId = "abc";
        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.REGISTER);
        when(event.getUserId()).thenReturn(userId);
        UserModel userModel = mock(UserModel.class);
        when(userProvider.getUserById(realm, userId)).thenReturn(userModel);

        provider.onEvent(event);

        verify(kafkaPublisher).publishCreate(any(UserEvent.class));
    }
}
