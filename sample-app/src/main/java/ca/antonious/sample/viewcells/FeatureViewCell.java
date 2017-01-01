package ca.antonious.sample.viewcells;

import android.view.View;
import android.widget.TextView;

import ca.antonious.sample.R;
import ca.antonious.sample.models.Feature;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.viewcells.BaseViewHolder;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-01-01.
 */

public class FeatureViewCell extends GenericViewCell<FeatureViewCell.FeatureViewHolder, Feature> {
    public FeatureViewCell(Feature feature) {
        super(feature);
    }

    @Override
    public int getLayoutId() {
        return R.layout.feature_list_item;
    }

    @Override
    public void bindViewCell(FeatureViewHolder viewHolder) {
        Feature feature = getModel();

        viewHolder.setTitle(feature.getTitle());
    }

    public interface OnFeatureClickListener {
        void onFeatureClicked(Feature feature);
    }

    @BindListener
    public void bindOnFeatureClick(FeatureViewHolder featureViewHolder, final OnFeatureClickListener onFeatureClickListener) {
        featureViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFeatureClickListener.onFeatureClicked(getModel());
            }
        });
    }

    public static class FeatureViewHolder extends BaseViewHolder {
        private TextView titleTextView;

        public FeatureViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.feature_title);
        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
