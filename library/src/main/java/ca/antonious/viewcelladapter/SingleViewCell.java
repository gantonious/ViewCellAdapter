package ca.antonious.viewcelladapter;

/**
 * Created by George on 2016-11-17.
 */

public abstract class SingleViewCell<VH extends BaseViewHolder> extends ViewCell<VH> {
    @Override
    public int getLayoutId(int position) {
        return getLayoutId();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemId(int position) {
        return getItemId();
    }

    @Override
    public void remove(int position) {

    }

    @Override
    public ViewCell get(int position) {
        return this;
    }


    @Override
    public void bindViewCell(VH viewHolder, int position) {
        bindViewCell(viewHolder);
    }

    @Override
    public void bindListeners(VH viewHolder, ListenerCollection listeners, int position) {
        bindListeners(viewHolder, listeners);
    }

    public abstract int getLayoutId();
    public abstract int getItemId();
    public abstract void bindViewCell(VH viewHolder);

    public void bindListeners(VH viewHolder, ListenerCollection listeners) {

    }
}
