package ca.antonious.viewcelladapter.viewcells;

import ca.antonious.viewcelladapter.BaseViewHolder;
import ca.antonious.viewcelladapter.ListenerCollection;

/**
 * Created by George on 2016-11-17.
 */

public abstract class AbstractViewCell<VH extends BaseViewHolder> {
    public abstract int getLayoutId();
    public abstract int getItemId();
    public abstract void bindViewCell(VH viewHolder);
    public abstract Class<? extends VH> getViewHolderClass();

    @Deprecated
    public abstract void bindListeners(VH viewHolder, ListenerCollection listeners);
}
