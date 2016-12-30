package ca.antonious.viewcelladapter.viewcells;

import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

/**
 * Created by George on 2016-11-17.
 */

public abstract class ViewCell<VH extends BaseViewHolder> extends AbstractViewCell<VH> {
    @Override
    public ViewHolderFactory getViewHolderFactory() {
        return new ReflectionBasedViewHolderFactory(getViewHolderClass());
    }

    @SuppressWarnings("unchecked")
    public Class<? extends VH> getViewHolderClass() {
        return (Class<? extends VH>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public static class ReflectionBasedViewHolderFactory implements ViewHolderFactory {
        private Class<? extends BaseViewHolder> viewHolderClass;

        public ReflectionBasedViewHolderFactory(Class<? extends BaseViewHolder> viewHolderClass) {
            this.viewHolderClass = viewHolderClass;
        }

        @Override
        public BaseViewHolder createViewHolder(View view) {
            try {
                Constructor<? extends BaseViewHolder> viewHolderConstructor = viewHolderClass.getConstructor(View.class);
                return viewHolderConstructor.newInstance(view);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
