package ca.antonious.sample.viewcells;

import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.eventhandling.ListenerCollection;
import ca.antonious.viewcelladapter.viewcells.ViewCell;

/**
 * Created by George on 2016-12-27.
 */

public interface ListenerBinder<TViewCell extends ViewCell<TViewHolder>, TViewHolder extends BaseViewHolder> {
    void bindListeners(TViewCell viewCell, TViewHolder viewHolder, ListenerCollection listenerCollection);
}
