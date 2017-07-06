package ca.antonious.viewcelladapter.viewcells;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

/**
 * Created by George on 2016-11-17.
 */

public abstract class AbstractViewCell<TViewHolder extends BaseViewHolder> implements Selectable {
    private boolean isSelected;

    public AbstractViewCell() {
        this.isSelected = false;
    }

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

    public int getLayoutId() {
        return -1;
    }

    public int getViewType() {
        return getClass().getCanonicalName().hashCode();
    }

    public int getTotalPerLine() {
        return 1;
    }

    public View createView(ViewGroup parent) {
        return inflate(parent, getLayoutId());
    }

    public TViewHolder createViewHolder(View view) {
        try {
            Constructor<? extends TViewHolder> viewHolderConstructor = getViewHolderClass().getConstructor(View.class);
            return viewHolderConstructor.newInstance(view);
        } catch (Exception e) {
            return null;
        }
    }

    public TViewHolder createViewHolder(ViewGroup parent) {
        return createViewHolder(createView(parent));
    }

    public View inflate(ViewGroup parent, @LayoutRes int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends TViewHolder> getViewHolderClass() {
        return (Class<? extends TViewHolder>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }


}
