package ca.antonious.viewcelladapter.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by George on 2016-12-30.
 */

public class CollectionUtils {
    public static <T> void setAll(List<T> baseList, Collection<? extends T> itemsToSet) {
        baseList.clear();
        baseList.addAll(itemsToSet);
    }

    public static <T> void prependAll(List<T> baseList, Collection<? extends T> itemsToPrepend) {
        List<T> newList = new ArrayList<>();
        newList.addAll(itemsToPrepend);
        newList.addAll(baseList);

        baseList.clear();
        baseList.addAll(newList);
    }
}
