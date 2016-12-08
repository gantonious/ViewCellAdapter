package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-12-08.
 */

public abstract class AbstractSection<VH extends BaseViewHolder> {
    public abstract AbstractViewCell get(int position);
    public abstract void remove(int position);
    public abstract int getLayoutId(int position);
    public abstract int getItemId(int position);
    public abstract int getItemCount();
    public abstract void bindListeners(VH viewHolder, ListenerCollection listeners, int position);
    public abstract void bindViewCell(VH viewHolder, int position);
    public abstract Class<? extends VH> getViewHolderClass(int position);
}
