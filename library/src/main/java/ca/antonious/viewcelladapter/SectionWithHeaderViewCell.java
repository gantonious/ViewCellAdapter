package ca.antonious.viewcelladapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class SectionWithHeaderViewCell extends Section {
    private AbstractViewCell headerViewCell;
    private boolean showHeaderIfEmpty = true;

    public boolean shouldShowHeaderIfEmpty() {
        return showHeaderIfEmpty;
    }

    public void setShowHeaderIfEmpty(boolean showHeaderIfEmpty) {
        this.showHeaderIfEmpty = showHeaderIfEmpty;
    }

    private boolean isSectionEmpty() {
        int count = super.getItemCount();
        return count == 0 || (count == 1 && headerViewCell != null && !showHeaderIfEmpty);
    }

    @Override
    public int getItemCount() {
        if (isSectionEmpty()) {
            return 0;
        }
        return super.getItemCount();
    }

    public void setSectionHeader(AbstractViewCell headerViewCell) {
        if (this.headerViewCell != null) {
            viewCells.remove(0);
        }
        viewCells.add(0, headerViewCell);
        this.headerViewCell = headerViewCell;
    }

    @Override
    public void setAll(Collection<? extends AbstractViewCell> viewCells) {
        this.viewCells.clear();
        if (headerViewCell != null) {
            this.viewCells.add(headerViewCell);
        }
        this.viewCells.addAll(viewCells);
    }

    @Override
    public void prependAll(Collection<? extends AbstractViewCell> viewCells) {
        List<AbstractViewCell> tempList = new ArrayList<>();
        if (headerViewCell != null) {
            tempList.add(headerViewCell);
        }
        tempList.addAll(viewCells);
        tempList.addAll(this.viewCells);

        this.viewCells.clear();
        this.viewCells.addAll(tempList);
    }

    @Override
    public void clear() {
        super.clear();
        if (headerViewCell != null) {
            this.viewCells.add(headerViewCell);
        }
    }
}
