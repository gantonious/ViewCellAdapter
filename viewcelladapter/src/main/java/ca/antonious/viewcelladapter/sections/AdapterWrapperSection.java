package ca.antonious.viewcelladapter.sections;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2017-07-05.
 */

public class AdapterWrapperSection<TViewHolder extends RecyclerView.ViewHolder> extends AbstractSection {
    private RecyclerView.Adapter<TViewHolder> adapter;

    public AdapterWrapperSection(RecyclerView.Adapter<TViewHolder> adapter) {
        this.adapter = adapter;
        this.adapter.registerAdapterDataObserver(new AdapterObserver());
    }

    @Override
    public AbstractViewCell get(int position) {
        return new AdapterViewCell(position);
    }

    @Override
    public int getInternalItemCount() {
        return adapter.getItemCount();
    }

    public class AdapterViewCell extends AbstractViewCell<TViewHolder> {
        private int position;

        public AdapterViewCell(int position) {
            this.position = position;
        }

        @Override
        public int getItemId() {
            return (int) adapter.getItemId(position);
        }

        @Override
        public void bindViewCell(TViewHolder viewHolder) {
            adapter.bindViewHolder(viewHolder, position);
        }

        @Override
        public TViewHolder createViewHolder(ViewGroup parent) {
            return adapter.createViewHolder(parent, adapter.getItemViewType(position));
        }
    }

    public class AdapterObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyDataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            notifyDataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyDataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyDataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyDataChanged();
        }
    }
}
