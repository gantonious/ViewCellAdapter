package ca.antonious.viewcelladapter.viewcells.builtins;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.security.SecureRandom;

import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;

/**
 * Created by George on 2017-01-01.
 */

public class StaticViewCell extends AbstractViewCell<BaseViewHolder> {
    private int layoutId;
    private int itemId;

    public StaticViewCell(@LayoutRes int layoutId) {
        this(layoutId, new SecureRandom().nextInt());
    }

    public StaticViewCell(@LayoutRes int layoutId, int itemId) {
        this.layoutId = layoutId;
        this.itemId = itemId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getItemId() {
        return itemId;
    }

    @Override
    public Function<View, BaseViewHolder> getViewHolderFactory() {
        return new Function<View, BaseViewHolder>() {
            @Override
            public BaseViewHolder apply(View input) {
                return new BaseViewHolder(input);
            }
        };
    }

    @Override
    public void bindViewCell(BaseViewHolder viewHolder) {

    }
}
