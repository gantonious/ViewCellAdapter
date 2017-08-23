package ca.antonious.viewcelladapter.viewcells;

import android.support.v7.widget.RecyclerView;

/**
 * Created by George on 2016-11-17.
 */

public abstract class GenericViewCell<TViewHolder extends RecyclerView.ViewHolder, TData> extends AbstractViewCell<TViewHolder> {
    private TData data;

    public GenericViewCell(TData data) {
        setData(data);
    }

    public TData getData() {
        return data;
    }

    public void setData(TData data) {
        this.data = data;
    }

    @Override
    public int getItemId() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return getData().toString();
    }
}
