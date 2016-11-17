package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class SectionViewCell extends ViewCell<BaseViewHolder> {
    private List<ViewCell> viewCells;

    public SectionViewCell() {
        this.viewCells = new ArrayList<>();
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

    @Override
    public int getLayoutId(int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        return viewCells.get(viewCellIndex).getLayoutId(internalViewCellIndex);
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
    public void bindViewCell(BaseViewHolder viewHolder, int position) {
        int viewCellIndex = ViewCellUtils.getViewCellIndex(viewCells, position);
        int internalViewCellIndex = ViewCellUtils.getInternalViewCellIndex(viewCells, position);

        viewCells.get(viewCellIndex).bindViewCell(viewHolder, internalViewCellIndex);
    }
}
