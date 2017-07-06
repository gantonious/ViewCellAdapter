package ca.antonious.viewcelladapter.viewcells.builtins;

import android.view.View;

import ca.antonious.viewcelladapter.R;
import ca.antonious.viewcelladapter.annotations.BindListener;

/**
 * Created by George on 2017-04-29.
 */

public class MaterialSettingsItemViewCell extends BaseMaterialSettingViewCell<MaterialSettingsItemViewCell.MaterialSettingsItemViewHolder> {
    public MaterialSettingsItemViewCell(int settingId,
                                        String label,
                                        int labelTextSizeSp,
                                        String secondaryText,
                                        int secondaryTextSizeSp) {
        super(settingId, label, labelTextSizeSp, secondaryText, secondaryTextSizeSp);
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public int getLayoutId() {
        return R.layout.material_settings_item_list_item;
    }

    @Override
    public MaterialSettingsItemViewHolder createViewHolder(View view) {
        return new MaterialSettingsItemViewHolder(view);
    }

    @Override
    public void bindViewCell(MaterialSettingsItemViewHolder viewHolder) {
        viewHolder.setLabel(label);
        viewHolder.setLabelTextSizeSp(labelTextSizeSp);
        viewHolder.setSecondaryText(secondaryText);
        viewHolder.setSecondaryTextTextSizeSp(secondaryTextSizeSp);
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

    public static class MaterialSettingsItemViewHolder extends BaseMaterialSettingViewCell.BaseMaterialSettingViewHolder {
        public MaterialSettingsItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class Builder extends BaseMaterialSettingViewCell.Builder<MaterialSettingsItemViewCell> {
        @Override
        public MaterialSettingsItemViewCell build() {
            return new MaterialSettingsItemViewCell(settingId, label, labelTextSizeSp, secondaryText, secondaryTextSizeSp);
        }
    }
}
