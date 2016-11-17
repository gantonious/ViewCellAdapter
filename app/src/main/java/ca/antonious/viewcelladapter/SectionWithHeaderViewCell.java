package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class SectionWithHeaderViewCell extends SectionViewCell {
    private ViewCell headerViewCell;

    public void setSectionHeader(ViewCell headerViewCell) {
        if (this.headerViewCell != null) {
            viewCells.remove(0);
        }
        viewCells.add(0, headerViewCell);
        this.headerViewCell = headerViewCell;
    }

    @Override
    public void setAll(Collection<? extends ViewCell> viewCells) {
        this.viewCells.clear();
        if (headerViewCell != null) {
            this.viewCells.add(headerViewCell);
        }
        this.viewCells.addAll(viewCells);
    }

    @Override
    public void prependAll(Collection<? extends ViewCell> viewCells) {
        List<ViewCell> tempList = new ArrayList<>();
        if (headerViewCell != null) {
            tempList.add(headerViewCell);
        }
        tempList.addAll(viewCells);
        tempList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(tempList);
    }
}
