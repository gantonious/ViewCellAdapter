package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-11-17.
 */

public abstract class ViewCell<VH extends BaseViewHolder> {
    public abstract int getLayoutId(int position);
    public abstract int getItemCount();
    public abstract void bindViewCell(VH viewHolder, int position);
    public abstract Class<? extends VH> getViewHolderClass(int position);
}
