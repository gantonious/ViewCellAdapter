package ca.antonious.sample.viewcells;

import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.sample.models.Sample;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-01-01.
 */

public class SampleViewCell extends GenericViewCell<SampleViewCell.SampleViewHolder, Sample> {
    public SampleViewCell(Sample sample) {
        super(sample);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_list_item;
    }

    @Override
    public void bindViewCell(SampleViewHolder viewHolder) {
        Sample sample = getModel();

        viewHolder.setTitle(sample.getTitle());
        viewHolder.setDescription(sample.getDescription());
    }

    public interface OnSampleClickListener {
        void onSampleClicked(Sample sample);
    }

    @BindListener
    public void bindOnFeatureClick(SampleViewHolder sampleViewHolder, final OnSampleClickListener onSampleClickListener) {
        sampleViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSampleClickListener.onSampleClicked(getModel());
            }
        });
    }

    public static class SampleViewHolder extends BaseViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;

        public SampleViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.sample_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.sample_description);
        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setDescription(String description) {
            descriptionTextView.setText(description);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
