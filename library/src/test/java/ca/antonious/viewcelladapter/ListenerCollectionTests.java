package ca.antonious.viewcelladapter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-18.
 */

public class ListenerCollectionTests {
    private RegisteredListener registeredListener;
    private ListenerCollection listenerCollection;

    @Before
    public void set_up() {
        registeredListener = new RegisteredListener() {
            @Override
            public void listen() {

            }
        };

        listenerCollection = new ListenerCollection();
        listenerCollection.addListener(registeredListener);
    }

    @Test
    public void test_getListener_ifListenerIsRegistered_returnsInstanceOfListener() {
        Object expectedListener = registeredListener;
        Object actualListener = listenerCollection.getListener(RegisteredListener.class);

        assertEquals(expectedListener, actualListener);
    }

    @Test
    public void test_getListener_ifListenerIsNotRegistered_returnsNull() {
        Object expectedListener = null;
        Object actualListener = listenerCollection.getListener(NotRegisteredListener.class);

        assertEquals(expectedListener, actualListener);
    }

    @Test
    public void test_removeListener_removesListener() {
        listenerCollection.removeListener(registeredListener);

        Object expectedListener = null;
        Object actualListener = listenerCollection.getListener(RegisteredListener.class);

        assertEquals(expectedListener, actualListener);
    }

    public interface RegisteredListener {
        void listen();
    }

    public interface NotRegisteredListener {
        void listen();
    }
}
