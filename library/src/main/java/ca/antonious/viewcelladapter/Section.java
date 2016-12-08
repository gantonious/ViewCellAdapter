package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class Section extends AbstractSection<BaseViewHolder> {
    protected List<AbstractViewCell> viewCells;

    public Section() {
        this.viewCells = new ArrayList<>();
    }

    public void add(AbstractViewCell viewCell) {
        this.viewCells.add(viewCell);
    }

    public void addAll(Collection<? extends AbstractViewCell> viewCells) {
        this.viewCells.addAll(viewCells);
    }

    public void setAll(Collection<? extends AbstractViewCell> viewCells) {
        this.viewCells.clear();
        this.viewCells.addAll(viewCells);
    }

    public void prependAll(Collection<? extends AbstractViewCell> viewCells) {
        List<AbstractViewCell> newList = new ArrayList<>();
        newList.addAll(viewCells);
        newList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(newList);
    }

    public void clear() {
        viewCells.clear();
    }

    @Override
    public AbstractViewCell get(int position) {
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
        return get(position).getItemId();
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
