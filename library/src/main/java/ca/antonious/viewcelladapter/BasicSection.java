package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class BasicSection implements Section<BaseViewHolder> {
    protected List<ViewCell> viewCells;

    public BasicSection() {
        this.viewCells = new ArrayList<>();
    }

    public void add(ViewCell viewCell) {
        this.viewCells.add(viewCell);
    }

    public void addAll(Collection<? extends ViewCell> viewCells) {
        this.viewCells.addAll(viewCells);
    }

    public void setAll(Collection<? extends ViewCell> viewCells) {
        this.viewCells.clear();
        this.viewCells.addAll(viewCells);
    }

    public void prependAll(Collection<? extends ViewCell> viewCells) {
        List<ViewCell> newList = new ArrayList<>();
        newList.addAll(viewCells);
        newList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(newList);
    }

    public void clear() {
        viewCells.clear();
    }

    @Override
    public ViewCell get(int position) {
        return viewCells.get(position);
    }

    @Override
    public void remove(int position) {
        viewCells.remove(position);
    }

    @Override
    public int getLayoutId(int position) {
        return get(position).getLayoutId();
    }

    @Override
    public int getItemId(int position) {
        return get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return viewCells.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindListeners(BaseViewHolder viewHolder, ListenerCollection listeners, int position) {
        get(position).bindListeners(viewHolder, listeners);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindViewCell(BaseViewHolder viewHolder, int position) {
        get(position).bindViewCell(viewHolder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends BaseViewHolder> getViewHolderClass(int position) {
        return get(position).getViewHolderClass();
    }
}
