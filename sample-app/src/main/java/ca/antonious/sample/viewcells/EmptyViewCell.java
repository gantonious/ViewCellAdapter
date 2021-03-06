package ca.antonious.sample.viewcells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2016-12-17.
 */

public class EmptyViewCell extends GenericViewCell<EmptyViewCell.ViewHolder, String> {

    public EmptyViewCell(String data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.empty_view_cell;
    }

    @Override
    public void bindViewCell(ViewHolder viewHolder) {
        viewHolder.setHeaderText(getData());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView emptyTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            emptyTextView = (TextView) itemView.findViewById(R.id.empty_text);
        }

        public void setHeaderText(String text) {
            emptyTextView.setText(text);
        }
    }
}
