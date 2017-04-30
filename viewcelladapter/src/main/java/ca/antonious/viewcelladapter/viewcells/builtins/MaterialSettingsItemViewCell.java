package ca.antonious.viewcelladapter.viewcells.builtins;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import ca.antonious.viewcelladapter.R;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;

/**
 * Created by George on 2017-04-29.
 */

public class MaterialSettingsItemViewCell extends AbstractViewCell<MaterialSettingsItemViewCell.MaterialSettingsItemViewHolder> {
    private int settingId;
    private String settingName;
    private int settingNameTextSizeSp;
    private String settingValue;
    private int settingValueTextSizeSp;

    public MaterialSettingsItemViewCell(int settingId,
                                         String settingName,
                                         int settingNameTextSizeSp,
                                         String settingValue,
                                         int settingValueTextSizeSp) {
        this.settingId = settingId;
        this.settingName = settingName;
        this.settingNameTextSizeSp = settingNameTextSizeSp;
        this.settingValue = settingValue;
        this.settingValueTextSizeSp = settingValueTextSizeSp;
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public int getLayoutId() {
        return R.layout.material_settings_item_list_item;
    }

    @Override
    public int getItemId() {
        return settingId;
    }

    @Override
    public Function<View, BaseViewHolder> getViewHolderFactory() {
        return new Function<View, BaseViewHolder>() {
            @Override
            public BaseViewHolder apply(View view) {
                return new MaterialSettingsItemViewHolder(view);
            }
        };
    }

    @Override
    public void bindViewCell(MaterialSettingsItemViewHolder viewHolder) {
        viewHolder.setSettingName(settingName);
        viewHolder.setSettingNameTextSizeSp(settingNameTextSizeSp);
        viewHolder.setSettingValue(settingValue);
        viewHolder.setSettingValueTextSizeSp(settingValueTextSizeSp);
    }

    public interface OnSettingClickedListener {
        void onSettingClicked(int settingId);
    }

    @BindListener
    public void bindOnSettingClickedListener(MaterialSettingsItemViewHolder viewHolder, final OnSettingClickedListener listener) {
        viewHolder.setOnBackgroundClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSettingClicked(settingId);
            }
        });
    }

    public static class MaterialSettingsItemViewHolder extends BaseViewHolder {
        private TextView settingNameTextView;
        private TextView settingValueTextView;

        public MaterialSettingsItemViewHolder(View itemView) {
            super(itemView);
            settingNameTextView = (TextView) itemView.findViewById(R.id.setting_name);
            settingValueTextView = (TextView) itemView.findViewById(R.id.setting_value);
        }

        public void setSettingName(String settingName) {
            settingNameTextView.setText(settingName);
        }

        public void setSettingNameTextSizeSp(int textSizeSp) {
            settingNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }

        public void setSettingValue(String settingValue) {
            if (settingValue.isEmpty()) {
                settingValueTextView.setVisibility(View.GONE);
            } else {
                settingValueTextView.setVisibility(View.VISIBLE);
            }
            settingValueTextView.setText(settingValue);
        }

        public void setSettingValueTextSizeSp(int textSizeSp) {
            settingValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }

        public void setOnBackgroundClickedListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    public static class Builder {
        private int settingId;
        private String settingName;
        private int settingNameTextSizeSp;
        private String settingValue;
        private int settingValueTextSizeSp;

        public Builder() {
            this.settingId = -1;
            this.settingName = "";
            this.settingNameTextSizeSp = 16;
            this.settingValue = "";
            this.settingValueTextSizeSp = 14;
        }

        public Builder id(int id) {
            this.settingId = id;
            return this;
        }

        public Builder name(String name) {
            this.settingName = name;
            return this;
        }

        public Builder nameTextSizeSp(int textSizeSp) {
            this.settingNameTextSizeSp = textSizeSp;
            return this;
        }

        public Builder value(String description) {
            this.settingValue = description;
            return this;
        }

        public Builder valueTextSizeSp(int textSizeSp) {
            this.settingValueTextSizeSp = textSizeSp;
            return this;
        }

        public MaterialSettingsItemViewCell build() {
            return new MaterialSettingsItemViewCell(settingId, settingName, settingNameTextSizeSp,
                    settingValue, settingValueTextSizeSp);
        }
    }

}
