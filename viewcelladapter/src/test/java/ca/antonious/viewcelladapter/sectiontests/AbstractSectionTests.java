package ca.antonious.viewcelladapter.sectiontests;

import org.junit.Test;

import java.util.Arrays;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.assertEquals;

/**
 * Created by george on 2017-08-21.
 */

public class AbstractSectionTests {
    @Test
    public void test_sectionIsVisibleByDefault() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");
        AbstractViewCell viewCell3 = new TestViewCell("ITEM-3");

        Section section = new Section();
        section.setAll(Arrays.asList(viewCell1, viewCell2, viewCell3));

        int expectedCount = 3;
        int actualCount = section.getItemCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void test_getItemCount_returnsItemCountIfSectionIsVisible() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");
        AbstractViewCell viewCell3 = new TestViewCell("ITEM-3");

        Section section = new Section();
        section.setAll(Arrays.asList(viewCell1, viewCell2, viewCell3));
        section.setVisibile(false);
        section.setVisibile(true);

        int expectedCount = 3;
        int actualCount = section.getItemCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void test_getItemCount_returnsZeroIfSectionIsNotVisible() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");
        AbstractViewCell viewCell3 = new TestViewCell("ITEM-3");

        Section section = new Section();
        section.setAll(Arrays.asList(viewCell1, viewCell2, viewCell3));
        section.setVisibile(false);

        int expectedCount = 0;
        int actualCount = section.getItemCount();

        assertEquals(expectedCount, actualCount);
    }
}
