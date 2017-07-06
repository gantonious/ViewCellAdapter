package ca.antonious.sample.viewcells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.sample.models.SampleModel;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-01-05.
 */

public class SampleModelViewCell extends GenericViewCell<SampleModelViewCell.SampleModelViewHolder, SampleModel> {
    public SampleModelViewCell(SampleModel sampleModel) {
        super(sampleModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_model_list_item;
    }

    @Override
    public void bindViewCell(SampleModelViewHolder viewHolder) {
        SampleModel sampleModel = getModel();
        viewHolder.setName(sampleModel.getName());
    }

    @Override
    public int getTotalPerLine() {
        return 3;
    }

    public interface OnSampleModelClickListener {
        void onSampleModelClick(SampleModel sampleModel);
    }

    @BindListener
    public void bindOnSampleModelClick(SampleModelViewHolder viewHolder, final OnSampleModelClickListener listener) {
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSampleModelClick(getModel());
            }
        });
    }

    public static class SampleModelViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;

        public SampleModelViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.sample_model_name);
        }

        public void setName(String name) {
            nameTextView.setText(name);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
