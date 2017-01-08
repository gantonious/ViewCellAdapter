package ca.antonious.viewcelladapter.viewcells;

import android.support.annotation.LayoutRes;
import android.view.View;

import ca.antonious.viewcelladapter.Function;

/**
 * Created by George on 2017-01-01.
 */

public class StaticViewCell extends AbstractViewCell<BaseViewHolder> {
    private int layoutId;

    public StaticViewCell(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getItemId() {
        return layoutId;
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
