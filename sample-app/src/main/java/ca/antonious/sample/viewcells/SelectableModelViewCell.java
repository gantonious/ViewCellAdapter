package ca.antonious.sample.viewcells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.sample.models.SelectableModel;
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
    public void bindViewCell(final SelectableModelViewHolder viewHolder) {
        SelectableModel selectableModel = getModel();

        viewHolder.setSelectionToggleListener(null);

        viewHolder.setName(selectableModel.getName());
        viewHolder.setIsSelected(isSelected());

        viewHolder.setSelectionToggleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSelectionState();
                viewHolder.setIsSelected(isSelected());
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

    public static class SelectableModelViewHolder extends RecyclerView.ViewHolder {
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

        public void setIsSelected(boolean isSelected) {
            selectionCheckBox.setChecked(isSelected);
        }

        public void setSelectionToggleListener(final View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
            selectionCheckBox.setOnClickListener(onClickListener);
        }
    }
}
