package ca.antonious.viewcelladapter.utilstests;

import org.junit.Test;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.utils.ViewCellUtils;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2017-05-14.
 */

public class GetSpanSizeTests {
    @Test
    public void test_totalPerLineOf1_spanCountOf3_shouldReturnSpanSizeOf3() {
        AbstractViewCell viewCell = new TestViewCell("id", 1);

        int expectedSpanSize = 3;
        int actualSpanSize = ViewCellUtils.getSpanSizeFor(viewCell, 3);

        assertEquals(expectedSpanSize, actualSpanSize);
    }

    @Test
    public void test_totalPerLineOf3_spanCountOf3_shouldReturnSpanSizeOf1() {
        AbstractViewCell viewCell = new TestViewCell("id", 3);

        int expectedSpanSize = 1;
        int actualSpanSize = ViewCellUtils.getSpanSizeFor(viewCell, 3);

        assertEquals(expectedSpanSize, actualSpanSize);
    }
}
