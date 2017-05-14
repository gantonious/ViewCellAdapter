package ca.antonious.viewcelladapter.utils;

import java.util.List;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

/**
 * Created by George on 2016-11-17.
 */

public class ViewCellUtils {
    public static int getTotalCount(List<? extends AbstractSection> sections) {
        int count = 0;

        for (AbstractSection section: sections) {
            count += section.getItemCount();
        }

        return count;
    }

    public static int getSectionIndex(List<? extends AbstractSection> sections, int listPosition) {
        int viewCellIndex = 0;

        for (AbstractSection section: sections) {
            if (listPosition > section.getItemCount() - 1) {
                viewCellIndex += 1;
                listPosition -= section.getItemCount();
            } else {
                break;
            }
        }

        return viewCellIndex;
    }

    public static int getViewCellIndex(List<? extends AbstractSection> sections, int listPosition) {
        for (AbstractSection section: sections) {
            if (listPosition > section.getItemCount() - 1) {
                listPosition -= section.getItemCount();
            } else {
                break;
            }
        }

        return listPosition;
    }

    public static AbstractViewCell getViewCell(List<? extends AbstractSection> sections, int listPosition) {
        int sectionIndex = ViewCellUtils.getSectionIndex(sections, listPosition);
        int viewCellIndex = ViewCellUtils.getViewCellIndex(sections, listPosition);

        return sections.get(sectionIndex).get(viewCellIndex);
    }

    public static int getSpanSizeFor(AbstractViewCell viewCell, int spanCount) {
        return spanCount % viewCell.getTotalPerLine() == 0
            ? spanCount / viewCell.getTotalPerLine()
            : spanCount;
    }
}
