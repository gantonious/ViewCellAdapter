package ca.antonious.viewcelladapter.viewcells;

import ca.antonious.viewcelladapter.BaseViewHolder;
import ca.antonious.viewcelladapter.ListenerCollection;

/**
 * Created by George on 2016-11-17.
 */

public abstract class ViewCell<VH extends BaseViewHolder> extends AbstractViewCell<VH> {
    public void bindListeners(VH viewHolder, ListenerCollection listeners) {

    }
}
