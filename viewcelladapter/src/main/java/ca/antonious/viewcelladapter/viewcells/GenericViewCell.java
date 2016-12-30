package ca.antonious.viewcelladapter.viewcells;

import java.lang.reflect.ParameterizedType;

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
}
