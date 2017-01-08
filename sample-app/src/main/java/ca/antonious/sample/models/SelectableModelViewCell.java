package ca.antonious.sample.models;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-01-08.
 */

public class SelectableModelViewCell extends GenericViewCell<SelectableModelViewCell.SelectableModelViewHolder, SelectableModel> {
    public SelectableModelViewCell(SelectableModel selectableModel) {
        super(selectableModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.selectable_model_list_item;
    }

    @Override
    public void bindViewCell(SelectableModelViewHolder viewHolder) {
        SelectableModel selectableModel = getModel();

        viewHolder.setName(selectableModel.getName());
        viewHolder.setSelectionToggleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSelectionState();
            }
        });
    }

    private void toggleSelectionState() {
        if (isSelected()) {
            deselect();
        } else {
            select();
        }
    }

    public static class SelectableModelViewHolder extends BaseViewHolder {
        private TextView nameTextView;
        private CheckBox selectionCheckBox;

        public SelectableModelViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.selectable_model_name);
            selectionCheckBox = (CheckBox) itemView.findViewById(R.id.selectable_model_is_selected);
        }

        public void setName(String name) {
            nameTextView.setText(name);
        }

        public void setSelectionToggleListener(final View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
            selectionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onClickListener.onClick(selectionCheckBox);
                }
            });
        }
    }
}
