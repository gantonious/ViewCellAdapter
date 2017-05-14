package ca.antonious.viewcelladapter.extensions;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.utils.ViewCellUtils;

/**
 * Created by George on 2017-05-14.
 */

public class ViewCellGridLayoutManager extends GridLayoutManager {
    private ViewCellAdapter viewCellAdapter;

    public ViewCellGridLayoutManager(Context context, ViewCellAdapter viewCellAdapter, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        this.viewCellAdapter = viewCellAdapter;
        setupSpanLookup();
    }

    public static ViewCellGridLayoutManager create(Context context, ViewCellAdapter viewCellAdapter, int spanCount) {
        return ViewCellGridLayoutManager.create(context, viewCellAdapter, spanCount, VERTICAL);
    }

    public static ViewCellGridLayoutManager create(Context context, ViewCellAdapter viewCellAdapter, int spanCount, int orientation) {
        return new ViewCellGridLayoutManager(context, viewCellAdapter, spanCount, orientation, false);
    }

    private void setupSpanLookup() {
        setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return ViewCellUtils.getSpanSizeFor(viewCellAdapter.get(position), getSpanCount());
            }
        });
    }
}
