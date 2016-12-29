package ca.antonious.viewcelladapter;

import java.util.HashMap;
import java.util.Map;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-28.
 */

public class BindListenersHelper {
    private static Map<Class<?>, ListenerBinder> cachedListenerBinders = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void bindListenersTo(AbstractViewCell viewCell, BaseViewHolder viewHolder, ListenerCollection listeners) {
        ListenerBinder listenerBinder = getListenerBinderFor(viewCell);

        if (listenerBinder != null) {
            listenerBinder.bindListeners(viewCell, viewHolder, listeners);
        }
    }

    private static ListenerBinder getListenerBinderFor(AbstractViewCell viewCell) {
        if (cachedListenerBinders.containsKey(viewCell.getClass())) {
            return cachedListenerBinders.get(viewCell.getClass());
        }

        try {
            String listenerBinderName = viewCell.getClass().getCanonicalName() + "_ListenerBinder";
            Class<?> listenerBinderClass = Class.forName(listenerBinderName);
            ListenerBinder listenerBinder = (ListenerBinder) listenerBinderClass.newInstance();
            cachedListenerBinders.put(viewCell.getClass(), listenerBinder);
        } catch (Exception e) {
            e.printStackTrace();
            cachedListenerBinders.put(viewCell.getClass(), null);
        }

        return cachedListenerBinders.get(viewCell.getClass());
    }
}
