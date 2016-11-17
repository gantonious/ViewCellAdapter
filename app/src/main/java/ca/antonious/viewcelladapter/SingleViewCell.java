package ca.antonious.viewcelladapter;

import java.lang.reflect.ParameterizedType;

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
    public void bindViewCell(VH viewHolder, int position) {
        bindViewCell(viewHolder);
    }

    public abstract int getLayoutId();
    public abstract void bindViewCell(VH viewHolder);
}
