package ca.antonious.viewcelladapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-18.
 */

public class ViewCellUtilsTests {
    @Test
    public void test_getTotalCount_ifSectionsListIsEmpty_thenReturnsZero() {
        List<AbstractSection> sections = new ArrayList<>();

        int expectedCount = 0;
        int actualCount = ViewCellUtils.getTotalCount(sections);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void test_getTotalCount_ifSectionsListIsNonEmpty_thenReturnsTotalViewCells() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        List<AbstractSection> sections = Arrays.<AbstractSection>asList(section1, section2);

        int expectedCount = 3;
        int actualCount = ViewCellUtils.getTotalCount(sections);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void test_getSectionIndex_getsCorrectSectionIndex() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        List<AbstractSection> sections = Arrays.<AbstractSection>asList(section1, section2);

        int expectedSectionIndex = 1;
        int actualSectionIndex = ViewCellUtils.getSectionIndex(sections, 2);

        assertEquals(expectedSectionIndex, actualSectionIndex);
    }

    @Test
    public void test_getViewCellIndex_getsCorrectViewCellIndex() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        List<AbstractSection> sections = Arrays.<AbstractSection>asList(section1, section2);

        int expectedViewCellIndex = 0;
        int actualViewCellIndex = ViewCellUtils.getViewCellIndex(sections, 2);

        assertEquals(expectedViewCellIndex, actualViewCellIndex);
    }

    @Test
    public void test_getViewCell_getsCorrectViewCell() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        List<AbstractSection> sections = Arrays.<AbstractSection>asList(section1, section2);

        AbstractViewCell expectedViewCell = section2Item1;
        AbstractViewCell actualViewCell = ViewCellUtils.getViewCell(sections, 2);

        assertEquals(expectedViewCell, actualViewCell);
    }
}
