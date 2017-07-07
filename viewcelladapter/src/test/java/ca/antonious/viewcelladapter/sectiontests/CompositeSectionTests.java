package ca.antonious.viewcelladapter.sectiontests;

import org.junit.Test;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.sections.CompositeSection;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-19.
 */

public class CompositeSectionTests {

    @Test
    public void test_getItemCount_returnsSumOfAllSectionsItemCount() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        CompositeSection compositeSection = new CompositeSection()
                .addSection(section1)
                .addSection(section2);

        int expectedItemCount = 3;
        int actualItemCount = compositeSection.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }

    @Test
    public void test_get_returnsExpectedViewCell() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        CompositeSection compositeSection = new CompositeSection()
                .addSection(section1)
                .addSection(section2);

        AbstractViewCell expectedViewCell = section2Item1;
        AbstractViewCell actualViewCell = compositeSection.get(2);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_removeSection() {
        AbstractViewCell section1Item1 = new TestViewCell("SECTION1:ITEM1");
        AbstractViewCell section1Item2 = new TestViewCell("SECTION1:ITEM2");
        AbstractViewCell section2Item1 = new TestViewCell("SECTION2:ITEM1");

        Section section1 = new Section();
        section1.add(section1Item1);
        section1.add(section1Item2);

        Section section2 = new Section();
        section2.add(section2Item1);

        CompositeSection compositeSection = new CompositeSection()
                .addSection(section1)
                .addSection(section2)
                .removeSection(section1);

        AbstractViewCell expectedViewCell = section2Item1;
        AbstractViewCell actualViewCell = compositeSection.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }
}
