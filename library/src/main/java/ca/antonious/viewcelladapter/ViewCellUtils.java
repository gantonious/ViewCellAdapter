package ca.antonious.viewcelladapter;

import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class ViewCellUtils {
    public static int getTotalCount(List<? extends Section> sections) {
        int count = 0;

        for (Section section: sections) {
            count += section.getItemCount();
        }

        return count;
    }

    public static int getSectionIndex(List<? extends Section> sections, int listPosition) {
        int viewCellIndex = 0;

        for (Section section: sections) {
            if (listPosition > section.getItemCount() - 1) {
                viewCellIndex += 1;
                listPosition -= section.getItemCount();
            } else {
                break;
            }
        }

        return viewCellIndex;
    }

    public static int getViewCellIndex(List<? extends Section> sections, int listPosition) {
        for (Section section: sections) {
            if (listPosition > section.getItemCount() - 1) {
                listPosition -= section.getItemCount();
            } else {
                break;
            }
        }

        return listPosition;
    }
}
