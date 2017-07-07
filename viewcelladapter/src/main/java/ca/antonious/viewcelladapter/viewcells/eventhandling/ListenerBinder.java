package ca.antonious.viewcelladapter.viewcells.eventhandling;

import android.support.v7.widget.RecyclerView;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;

/**
 * Created by George on 2016-12-28.
 */

public interface ListenerBinder<TViewCell extends AbstractViewCell<TViewHolder>, TViewHolder extends RecyclerView.ViewHolder> {
    void bindListeners(TViewCell viewCell, TViewHolder viewHolder, ListenerCollection listenerCollection);
}
