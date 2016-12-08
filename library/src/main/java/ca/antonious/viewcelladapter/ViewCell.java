package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-11-17.
 */

public interface ViewCell<VH extends BaseViewHolder> {
    int getLayoutId();
    int getItemId();
    void bindListeners(VH viewHolder, ListenerCollection listeners);
    void bindViewCell(VH viewHolder);
    Class<? extends VH> getViewHolderClass();
}
