package ca.antonious.viewcelladapter.viewcells;

import android.view.View;

import ca.antonious.viewcelladapter.Function;

/**
 * Created by George on 2016-11-17.
 */

public abstract class AbstractViewCell<VH extends BaseViewHolder> implements Selectable {
    private boolean isSelected;

    public AbstractViewCell() {
        this.isSelected = false;
    }

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

    public abstract int getLayoutId();
    public abstract int getItemId();
    public abstract void bindViewCell(VH viewHolder);
    public abstract Function<View, BaseViewHolder> getViewHolderFactory();
}
