package ca.antonious.viewcelladapter.viewcells.builtins;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.Random;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;

/**
 * Created by George on 2017-01-01.
 */

public class StaticViewCell extends AbstractViewCell<BaseViewHolder> {
    private int layoutId;
    private int itemId;

    public StaticViewCell(@LayoutRes int layoutId) {
        this(layoutId, new Random(System.nanoTime()).nextInt());
    }

    public StaticViewCell(@LayoutRes int layoutId, int itemId) {
        this.layoutId = layoutId;
        this.itemId = itemId;
    }

    @Override
    public int getViewType() {
        return getLayoutId();
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
    public BaseViewHolder createViewHolder(View view) {
        return new BaseViewHolder(view);
    }

    @Override
    public void bindViewCell(BaseViewHolder viewHolder) {

    }
}
