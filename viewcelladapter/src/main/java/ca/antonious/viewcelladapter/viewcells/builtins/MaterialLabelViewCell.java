package ca.antonious.viewcelladapter.viewcells.builtins;

import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import ca.antonious.viewcelladapter.R;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-04-26.
 */

public class MaterialLabelViewCell extends GenericViewCell<MaterialLabelViewCell.MaterialLabelViewHolder, String> {
    private int labelColor;
    private int textSizeSp;

    public MaterialLabelViewCell(String label,
                                 int labelColor,
                                 int textSizeSp) {
        super(label);
        this.labelColor = labelColor;
        this.textSizeSp = textSizeSp;
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public int getLayoutId() {
        return R.layout.material_label_list_item;
    }

    @Override
    public MaterialLabelViewHolder createViewHolder(View view) {
        return new MaterialLabelViewHolder(view);
    }

    @Override
    public void bindViewCell(MaterialLabelViewHolder viewHolder) {
        viewHolder.setLabel(getData());
        viewHolder.setLabelColor(labelColor);
        viewHolder.setLabelTextSizeSp(textSizeSp);
    }

    public static class MaterialLabelViewHolder extends BaseViewHolder {
        private TextView labelTextView;

        public MaterialLabelViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(R.id.label_text);
        }

        public void setLabel(String label) {
            labelTextView.setText(label);
        }

        public void setLabelColor(int color) {
            labelTextView.setTextColor(color);
        }

        public void setLabelTextSizeSp(int size) {
            labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    public static class Builder {
        private String label;
        private int labelColor;
        private int textSizeSp;

        public Builder() {
            this.label = "";
            this.labelColor = 0x0;
            this.textSizeSp = 16;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder textColor(@ColorInt int color) {
            this.labelColor = color;
            return this;
        }

        public Builder textSizeSp(int size) {
            this.textSizeSp = size;
            return this;
        }

        public MaterialLabelViewCell build() {
            return new MaterialLabelViewCell(label, labelColor, textSizeSp);
        }
    }
}
