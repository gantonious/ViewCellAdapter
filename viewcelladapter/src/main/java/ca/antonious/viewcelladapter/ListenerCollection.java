package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 2016-11-21.
 */

public class ListenerCollection {
    private List<Object> listeners;

    public ListenerCollection() {
        listeners = new ArrayList<>();
    }

    public void addListener(Object listener) {
        listeners.add(listener);
    }

    public void removeListener(Object listener) {
        listeners.remove(listener);
    }

    public <T> T getListener(Class<? extends T> listenerType) {
        for (Object listener: listeners) {
            if (listenerType.isAssignableFrom(listener.getClass())) {
                return (T) listener;
            }
        }
        return null;
    }
}
