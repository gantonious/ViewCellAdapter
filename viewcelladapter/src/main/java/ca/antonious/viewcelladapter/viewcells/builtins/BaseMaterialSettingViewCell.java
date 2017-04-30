package ca.antonious.viewcelladapter.viewcells.builtins;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import ca.antonious.viewcelladapter.R;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;

/**
 * Created by George on 2017-04-29.
 */

public abstract class BaseMaterialSettingViewCell<TViewCell extends BaseMaterialSettingViewCell.BaseMaterialSettingViewHolder> extends AbstractViewCell<TViewCell> {
    protected int settingId;
    protected String label;
    protected int labelTextSizeSp;
    protected String secondaryText;
    protected int secondaryTextSizeSp;

    public BaseMaterialSettingViewCell(int settingId,
                                       String label,
                                       int labelTextSizeSp,
                                       String secondaryText,
                                       int secondaryTextSizeSp) {
        this.settingId = settingId;
        this.label = label;
        this.labelTextSizeSp = labelTextSizeSp;
        this.secondaryText = secondaryText;
        this.secondaryTextSizeSp = secondaryTextSizeSp;
    }

    @Override
    public int getItemId() {
        return settingId;
    }

    public static class BaseMaterialSettingViewHolder extends BaseViewHolder {
        private TextView labelTextView;
        private TextView secondaryTextTextView;

        public BaseMaterialSettingViewHolder(View itemView) {
            super(itemView);
            labelTextView = (TextView) itemView.findViewById(R.id.label);
            secondaryTextTextView = (TextView) itemView.findViewById(R.id.secondary_text);
        }

        public void setLabel(String label) {
            labelTextView.setText(label);
        }

        public void setLabelTextSizeSp(int textSizeSp) {
            labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }

        public void setSecondaryText(String secondaryText) {
            if (secondaryText.isEmpty()) {
                secondaryTextTextView.setVisibility(View.GONE);
            } else {
                secondaryTextTextView.setVisibility(View.VISIBLE);
            }
            secondaryTextTextView.setText(secondaryText);
        }

        public void setSecondaryTextTextSizeSp(int textSizeSp) {
            secondaryTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);

        }

        public void setOnBackgroundClickedListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    public static abstract class Builder<T extends BaseMaterialSettingViewCell> {
        protected int settingId;
        protected String label;
        protected int labelTextSizeSp;
        protected String secondaryText;
        protected int secondaryTextSizeSp;

        public Builder() {
            this.settingId = -1;
            this.label = "";
            this.labelTextSizeSp = 16;
            this.secondaryText = "";
            this.secondaryTextSizeSp = 12;
        }

        public Builder id(int id) {
            this.settingId = id;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder labelTextSizeSp(int textSizeSp) {
            this.labelTextSizeSp = textSizeSp;
            return this;
        }

        public Builder secondaryText(String secondaryText) {
            this.secondaryText = secondaryText;
            return this;
        }

        public Builder secondaryTextSizeSp(int textSizeSp) {
            this.secondaryTextSizeSp = textSizeSp;
            return this;
        }

        public abstract T build();
    }
}
