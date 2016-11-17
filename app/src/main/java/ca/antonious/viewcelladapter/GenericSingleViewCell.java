package ca.antonious.viewcelladapter;

import java.lang.reflect.ParameterizedType;

/**
 * Created by George on 2016-11-17.
 */

public abstract class GenericSingleViewCell<T, VH extends BaseViewHolder> extends SingleViewCell<VH> {
    private T model;

    public GenericSingleViewCell(T model) {
        setModel(model);
    }

    public T getModel() {
        return model;
    }

    private void setModel(T model) {
        this.model = model;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends VH> getViewHolderClass(int position) {
        return (Class<? extends VH>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
