package ca.antonious.viewcelladapter.viewcells;

import java.lang.reflect.ParameterizedType;

import ca.antonious.viewcelladapter.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.ViewCell;

/**
 * Created by George on 2016-11-17.
 */

public abstract class GenericViewCell<VH extends BaseViewHolder, T> extends ViewCell<VH> {
    private T model;

    public GenericViewCell(T model) {
        setModel(model);
    }

    public T getModel() {
        return model;
    }

    private void setModel(T model) {
        this.model = model;
    }

    @Override
    public int getItemId() {
        return model.hashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends VH> getViewHolderClass() {
        return (Class<? extends VH>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
