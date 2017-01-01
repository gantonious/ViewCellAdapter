package ca.antonious.viewcelladapter.viewcells;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import ca.antonious.viewcelladapter.Function;

/**
 * Created by George on 2016-11-17.
 */

public abstract class ViewCell<VH extends BaseViewHolder> extends AbstractViewCell<VH> {
    @Override
    public Function<View, BaseViewHolder> getViewHolderFactory() {
        return new ReflectionBasedViewHolderFactory(getViewHolderClass());
    }

    @SuppressWarnings("unchecked")
    public Class<? extends VH> getViewHolderClass() {
        return (Class<? extends VH>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static class ReflectionBasedViewHolderFactory implements Function<View, BaseViewHolder> {
        private Class<? extends BaseViewHolder> viewHolderClass;

        public ReflectionBasedViewHolderFactory(Class<? extends BaseViewHolder> viewHolderClass) {
            this.viewHolderClass = viewHolderClass;
        }

        @Override
        public BaseViewHolder apply(View view) {
            try {
                Constructor<? extends BaseViewHolder> viewHolderConstructor = viewHolderClass.getConstructor(View.class);
                return viewHolderConstructor.newInstance(view);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
