package ca.antonious.viewcelladapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-28.
 */

public class BindListenersHelper {
    private static Map<Class<?>, ListenerBinder> cachedListenerBinders = new HashMap<>();
    private static Map<Class<?>, Set<ListenerBinder>> listenerBinderMappings = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void bindListenersTo(AbstractViewCell viewCell, BaseViewHolder viewHolder, ListenerCollection listeners) {
        for (ListenerBinder listenerBinder: getListenerBindersFor(viewCell.getClass())) {
            listenerBinder.bindListeners(viewCell, viewHolder, listeners);
        }
    }

    private static Set<ListenerBinder> getListenerBindersFor(Class<? extends AbstractViewCell> viewCellClass) {
        if (!listenerBinderMappings.containsKey(viewCellClass)) {
            createListenerBinderMappingsFor(viewCellClass);
        }
        return listenerBinderMappings.get(viewCellClass.getClass());
    }

    private static void createListenerBinderMappingsFor(Class<? extends AbstractViewCell> viewCellClass) {
        Class<?> currentClass = viewCellClass;
        Set<ListenerBinder> listenerBinders = new HashSet<>();

        while (currentClass != null) {
            ListenerBinder listenerBinder = getListenerBinderFor(currentClass);

            if (listenerBinder != null) {
                listenerBinders.add(listenerBinder);
            }
            
            currentClass = currentClass.getSuperclass();
        }

        listenerBinderMappings.put(viewCellClass, listenerBinders);
    }

    private static ListenerBinder getListenerBinderFor(Class<?> clazz) {
        if (!cachedListenerBinders.containsKey(clazz)) {
            loadListenerBinderFor(clazz);
        }
        return cachedListenerBinders.get(clazz);
    }

    private static void loadListenerBinderFor(Class<?> clazz) {
        try {
            String listenerBinderName = clazz.getCanonicalName() + "_ListenerBinder";
            Class<?> listenerBinderClass = Class.forName(listenerBinderName);
            ListenerBinder listenerBinder = (ListenerBinder) listenerBinderClass.newInstance();
            cachedListenerBinders.put(clazz, listenerBinder);
        } catch (Exception e) {
            cachedListenerBinders.put(clazz, null);
        }
    }
}
