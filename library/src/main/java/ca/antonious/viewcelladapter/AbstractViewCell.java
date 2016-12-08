package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-11-17.
 */

public abstract class AbstractViewCell<VH extends BaseViewHolder> {
    public abstract int getLayoutId();
    public abstract int getItemId();
    public abstract void bindListeners(VH viewHolder, ListenerCollection listeners);
    public abstract void bindViewCell(VH viewHolder);
    public abstract Class<? extends VH> getViewHolderClass();
}
