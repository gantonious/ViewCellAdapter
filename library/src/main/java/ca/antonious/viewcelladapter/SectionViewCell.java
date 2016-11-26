package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class SectionViewCell extends ViewCell<BaseViewHolder> {
    protected List<ViewCell> viewCells;

    public SectionViewCell() {
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
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        return viewCells.get(viewCellIndex).get(internalViewCellIndex);
    }

    @Override
    public void remove(int position) {

    }

    @Override
    public int getLayoutId(int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        return viewCells.get(viewCellIndex).getLayoutId(internalViewCellIndex);
    }

    @Override
    public int getItemId(int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        return viewCells.get(viewCellIndex).getItemId(internalViewCellIndex);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (ViewCell viewCell: viewCells) {
            count += viewCell.getItemCount();
        }
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindListeners(BaseViewHolder viewHolder, ListenerCollection listeners, int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        viewCells.get(viewCellIndex).bindListeners(viewHolder, listeners, internalViewCellIndex);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindViewCell(BaseViewHolder viewHolder, int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        viewCells.get(viewCellIndex).bindViewCell(viewHolder, internalViewCellIndex);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends BaseViewHolder> getViewHolderClass(int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        return viewCells.get(viewCellIndex).getViewHolderClass(internalViewCellIndex);
    }
}
