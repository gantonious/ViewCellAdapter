package ca.antonious.viewcelladapter.viewcells.builtins;

import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import ca.antonious.viewcelladapter.R;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.internal.Function;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;

/**
 * Created by George on 2017-04-26.
 */

public class MaterialToggleSettingViewCell extends BaseMaterialSettingViewCell<MaterialToggleSettingViewCell.MaterialToggleSettingViewHolder> {
    private boolean isChecked;

    public MaterialToggleSettingViewCell(int settingId,
                                         String label,
                                         int labelTextSizeSp,
                                         String secondaryText,
                                         int secondaryTextSizeSp,
                                         boolean isChecked) {
        super(settingId, label, labelTextSizeSp, secondaryText, secondaryTextSizeSp);
        this.isChecked = isChecked;
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public int getLayoutId() {
        return R.layout.material_toggle_setting_list_item;
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
                return new MaterialToggleSettingViewHolder(view);
            }
        };
    }

    @Override
    public void bindViewCell(MaterialToggleSettingViewHolder viewHolder) {
        viewHolder.setLabel(label);
        viewHolder.setLabelTextSizeSp(labelTextSizeSp);
        viewHolder.setSecondaryText(secondaryText);
        viewHolder.setSecondaryTextTextSizeSp(secondaryTextSizeSp);
        viewHolder.setSwitchState(isChecked);
    }

    public interface OnSettingToggledListener {
        void onSettingToggled(int settingId, boolean isOn);
    }

    @BindListener(bindIfNull = true)
    public void bindOnSettingToggledListener(final MaterialToggleSettingViewHolder viewHolder, final OnSettingToggledListener listener) {
        viewHolder.setOnBackgroundClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.setSwitchState(!isChecked);
            }
        });

        viewHolder.setOnSwitchStateChangedListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                isChecked = checked;
                if (listener != null) {
                    listener.onSettingToggled(settingId, checked);
                }
            }
        });
    }

    public class MaterialToggleSettingViewHolder extends BaseMaterialSettingViewCell.BaseMaterialSettingViewHolder {
        private Switch settingSwitch;

        public MaterialToggleSettingViewHolder(View itemView) {
            super(itemView);
            settingSwitch = (Switch) itemView.findViewById(R.id.setting_switch);
        }

        public void setSwitchState(boolean isChecked) {
            settingSwitch.setChecked(isChecked);
        }

        public void setOnSwitchStateChangedListener(CompoundButton.OnCheckedChangeListener onSwitchStateChangedListener) {
            settingSwitch.setOnCheckedChangeListener(onSwitchStateChangedListener);
        }
    }

    public static class Builder extends BaseMaterialSettingViewCell.Builder<MaterialToggleSettingViewCell> {
        private boolean isChecked;

        public Builder() {
            super();
            this.isChecked = false;
        }

        public Builder checked(boolean isChecked) {
            this.isChecked = isChecked;
            return this;
        }

        public MaterialToggleSettingViewCell build() {
            return new MaterialToggleSettingViewCell(settingId, label, labelTextSizeSp,
                    secondaryText, secondaryTextSizeSp, isChecked);
        }
    }
}
