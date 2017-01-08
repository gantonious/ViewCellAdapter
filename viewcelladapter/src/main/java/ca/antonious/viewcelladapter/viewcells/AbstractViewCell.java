package ca.antonious.viewcelladapter.viewcells;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import ca.antonious.viewcelladapter.Function;

/**
 * Created by George on 2016-11-17.
 */

public abstract class AbstractViewCell<TViewHolder extends BaseViewHolder> implements Selectable {
    private boolean isSelected;

    public AbstractViewCell() {
        this.isSelected = false;
    }

    public abstract int getLayoutId();
    public abstract int getItemId();
    public abstract void bindViewCell(TViewHolder viewHolder);

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void select() {
        isSelected = true;
    }

    @Override
    public void deselect() {
        isSelected = false;
    }

    public Function<View, BaseViewHolder> getViewHolderFactory() {
        return new ReflectionBasedViewHolderFactory(getViewHolderClass());
    }

    @SuppressWarnings("unchecked")
    public Class<? extends TViewHolder> getViewHolderClass() {
        return (Class<? extends TViewHolder>) ((ParameterizedType) getClass()
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
