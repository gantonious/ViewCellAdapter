package ca.antonious.viewcelladapter;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.ViewCell;

/**
 * Created by George on 2016-12-28.
 */

public interface ListenerBinder<TViewCell extends AbstractViewCell<TViewHolder>, TViewHolder extends BaseViewHolder> {
    void bindListeners(TViewCell viewCell, TViewHolder viewHolder, ListenerCollection listenerCollection);
}
