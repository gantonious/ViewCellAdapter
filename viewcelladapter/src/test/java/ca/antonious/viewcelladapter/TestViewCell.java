package ca.antonious.viewcelladapter;

import android.view.View;

import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2016-12-18.
 */

public class TestViewCell extends GenericViewCell<TestViewCell.ViewHolder, String> {
    private int totalPerLine;

    public TestViewCell(String identifier) {
        this(identifier, 1);
    }

    public TestViewCell(String identifier, int totalPerLine) {
        super(identifier);
        this.totalPerLine = totalPerLine;
    }

    @Override
    public int getLayoutId() {
        return getModel().length();
    }

    @Override
    public int getItemId() {
        return getModel().length();
    }

    @Override
    public int getTotalPerLine() {
        return totalPerLine;
    }

    @Override
    public void bindViewCell(ViewHolder viewHolder) {

    }

    @Override
    public Class<ViewHolder> getViewHolderClass() {
        return ViewHolder.class;
    }

    public static class ViewHolder extends BaseViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public String toString() {
        return String.format("[ViewCell: %s]", getModel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestViewCell that = (TestViewCell) o;

        return getModel() != null ? getModel().equals(that.getModel()) : that.getModel() == null;
    }

    @Override
    public int hashCode() {
        return getModel() != null ? getModel().hashCode() : 0;
    }
}
