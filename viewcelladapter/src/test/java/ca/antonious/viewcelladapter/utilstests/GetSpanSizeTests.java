package ca.antonious.viewcelladapter.utilstests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.utils.ViewCellUtils;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2017-05-14.
 */

@RunWith(Parameterized.class)
public class GetSpanSizeTests {
    private int spanCount;
    private int totalPerLine;
    private int expectedSpanSize;

    public GetSpanSizeTests(int spanCount, int totalPerLine, int expectedSpanSize) {
        this.spanCount = spanCount;
        this.totalPerLine = totalPerLine;
        this.expectedSpanSize = expectedSpanSize;
    }

    @Parameterized.Parameters(name = "(spanCount:{0}, totalPerLine:{1}) -> spanSize:{2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 3, 3, 1 },
                { 3, 2, 2 },
                { 3, 1, 3 },
                { 4, 4, 1 },
                { 4, 3, 2 },
                { 4, 2, 2 },
                { 4, 1, 4 }
        });
    }

    @Test
    public void test() {
        AbstractViewCell viewCell = new TestViewCell("id", totalPerLine);
        assertEquals(expectedSpanSize, ViewCellUtils.getSpanSizeFor(viewCell, spanCount));
    }
}
