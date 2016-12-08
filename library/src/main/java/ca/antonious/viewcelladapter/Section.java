package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-12-08.
 */

public interface Section<VH extends BaseViewHolder> {
    ViewCell get(int position);
    void remove(int position);
    int getLayoutId(int position);
    int getItemId(int position);
    int getItemCount();
    void bindListeners(VH viewHolder, ListenerCollection listeners, int position);
    void bindViewCell(VH viewHolder, int position);
    Class<? extends VH> getViewHolderClass(int position);
}
