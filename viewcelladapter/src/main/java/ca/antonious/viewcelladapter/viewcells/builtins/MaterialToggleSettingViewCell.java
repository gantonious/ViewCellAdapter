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

public class MaterialToggleSettingViewCell extends AbstractViewCell<MaterialToggleSettingViewCell.MaterialToggleSettingViewHolder> {
    private int settingId;
    private String settingName;
    private int settingNameTextSizeSp;
    private String settingDescription;
    private int settingDescriptionTextSizeSp;
    private boolean isChecked;

    public MaterialToggleSettingViewCell(int settingId,
                                         String settingName,
                                         int settingNameTextSizeSp,
                                         String settingDescription,
                                         int settingDescriptionTextSizeSp,
                                         boolean isChecked) {
        this.settingId = settingId;
        this.settingName = settingName;
        this.settingNameTextSizeSp = settingNameTextSizeSp;
        this.settingDescription = settingDescription;
        this.settingDescriptionTextSizeSp = settingDescriptionTextSizeSp;
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
        viewHolder.setSettingName(settingName);
        viewHolder.setSettingNameTextSizeSp(settingNameTextSizeSp);
        viewHolder.setSettingDescription(settingDescription);
        viewHolder.setSettingDescriptionTextSizeSp(settingDescriptionTextSizeSp);
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

    public class MaterialToggleSettingViewHolder extends BaseViewHolder {
        private TextView settingNameTextView;
        private TextView settingDescriptionTextView;
        private Switch settingSwitch;

        public MaterialToggleSettingViewHolder(View itemView) {
            super(itemView);
            settingNameTextView = (TextView) itemView.findViewById(R.id.setting_name);
            settingDescriptionTextView = (TextView) itemView.findViewById(R.id.setting_description);
            settingSwitch = (Switch) itemView.findViewById(R.id.setting_switch);
        }

        public void setSettingName(String settingName) {
            settingNameTextView.setText(settingName);
        }

        public void setSettingNameTextSizeSp(int textSizeSp) {
            settingNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }

        public void setSettingDescription(String settingDescription) {
            if (settingDescription.isEmpty()) {
                settingDescriptionTextView.setVisibility(View.GONE);
            } else {
                settingDescriptionTextView.setVisibility(View.VISIBLE);
            }
            settingDescriptionTextView.setText(settingDescription);
        }

        private void setSettingDescriptionTextSizeSp(int textSizeSp) {
            settingDescriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);

        }

        public void setSwitchState(boolean isChecked) {
            settingSwitch.setChecked(isChecked);
        }

        public void setOnBackgroundClickedListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

        public void setOnSwitchStateChangedListener(CompoundButton.OnCheckedChangeListener onSwitchStateChangedListener) {
            settingSwitch.setOnCheckedChangeListener(onSwitchStateChangedListener);
        }
    }

    public static class Builder {
        private int settingId;
        private String settingName;
        private int settingNameTextSizeSp;
        private String settingDescription;
        private int settingDescriptionTextSizeSp;
        private boolean isChecked;

        public Builder() {
            this.settingId = -1;
            this.settingName = "";
            this.settingNameTextSizeSp = 16;
            this.settingDescription = "";
            this.settingDescriptionTextSizeSp = 12;
            this.isChecked = false;
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

        public Builder description(String description) {
            this.settingDescription = description;
            return this;
        }

        public Builder descriptionTextSizeSp(int textSizeSp) {
            this.settingDescriptionTextSizeSp = textSizeSp;
            return this;
        }

        public Builder checked(boolean isChecked) {
            this.isChecked = isChecked;
            return this;
        }

        public MaterialToggleSettingViewCell build() {
            return new MaterialToggleSettingViewCell(settingId, settingName, settingNameTextSizeSp,
                    settingDescription, settingDescriptionTextSizeSp, isChecked);
        }
    }
}
