package ca.antonious.viewcelladapter;

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
}
