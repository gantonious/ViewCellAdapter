package ca.antonious.viewcelladapter;

import java.util.List;

/**
 * Created by George on 2016-11-17.
 */

public class ViewCellUtils {
    public static int getTotalCount(List<? extends ViewCell> viewCells) {
        int count = 0;

        for (ViewCell viewCell: viewCells) {
            count += viewCell.getItemCount();
        }

        return count;
    }

    public static int getViewCellIndex(List<? extends ViewCell> viewCells, int listPosition) {
        int viewCellIndex = 0;

        for (ViewCell viewCell : viewCells) {
            if (listPosition > viewCell.getItemCount() - 1) {
                viewCellIndex += 1;
                listPosition -= viewCell.getItemCount();
            } else {
                break;
            }
        }

        return viewCellIndex;
    }

    public static int getInternalViewCellIndex(List<? extends ViewCell> viewCells, int listPosition) {
        for (ViewCell viewCell: viewCells) {
            if (listPosition > viewCell.getItemCount() - 1) {
                listPosition -= viewCell.getItemCount();
            } else {
                break;
            }
        }

        return listPosition;
    }
}
