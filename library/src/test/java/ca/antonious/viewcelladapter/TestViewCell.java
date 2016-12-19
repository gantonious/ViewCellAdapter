package ca.antonious.viewcelladapter;

import android.view.View;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-12-18.
 */

public class TestViewCell extends AbstractViewCell<TestViewCell.ViewHolder> {
    private String identifier;

    public TestViewCell(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int getLayoutId() {
        return identifier.length();
    }

    @Override
    public int getItemId() {
        return identifier.length();
    }

    @Override
    public void bindListeners(ViewHolder viewHolder, ListenerCollection listeners) {

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
        return String.format("[ViewCell: %s]", identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestViewCell that = (TestViewCell) o;

        return identifier != null ? identifier.equals(that.identifier) : that.identifier == null;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }
}
