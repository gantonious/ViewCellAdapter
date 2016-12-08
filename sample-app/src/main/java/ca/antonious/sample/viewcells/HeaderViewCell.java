package ca.antonious.sample.viewcells;

import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.viewcelladapter.BaseViewHolder;
import ca.antonious.viewcelladapter.GenericViewCell;

/**
 * Created by George on 2016-11-17.
 */

public class HeaderViewCell extends GenericViewCell<HeaderViewCell.ViewHolder, String> {

    public HeaderViewCell(String model) {
        super(model);
    }

    @Override
    public int getLayoutId() {
        return R.layout.header_list_item;
    }

    @Override
    public void bindViewCell(HeaderViewCell.ViewHolder viewHolder) {
        viewHolder.setHeaderText(getModel());
    }

    public static class ViewHolder extends BaseViewHolder {
        private TextView headerTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            headerTextView = (TextView) itemView.findViewById(R.id.header_text);
        }

        public void setHeaderText(String text) {
            headerTextView.setText(text);
        }
    }
}
