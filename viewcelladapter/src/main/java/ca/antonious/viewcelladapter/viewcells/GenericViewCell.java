package ca.antonious.viewcelladapter.viewcells;

/**
 * Created by George on 2016-11-17.
 */

public abstract class GenericViewCell<TViewHolder extends BaseViewHolder, TModel> extends ViewCell<TViewHolder> {
    private TModel model;

    public GenericViewCell(TModel model) {
        setModel(model);
    }

    public TModel getModel() {
        return model;
    }

    private void setModel(TModel model) {
        this.model = model;
    }

    @Override
    public int getItemId() {
        return model.hashCode();
    }
}
