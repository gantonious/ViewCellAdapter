package ca.antonious.viewcelladapter;

import java.lang.reflect.ParameterizedType;

/**
 * Created by George on 2016-11-17.
 */

public abstract class SingleViewCell<VH extends BaseViewHolder> extends ViewCell<VH> {
    @Override
    public int getLayoutId(int position) {
        return getItemCount();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void bindViewCell(VH viewHolder, int position) {
        bindViewCell(viewHolder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends VH> getViewHolderClass(int position) {
        return (Class<? extends VH>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract int getLayoutId();
    public abstract void bindViewCell(VH viewHolder);
}
