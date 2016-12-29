package ca.antonious.viewcelladapter.decoratortests;

import org.junit.Test;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.decorators.EmptySectionDecorator;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-18.
 */

public class EmptySectionDecoratorTests {
    @Test
    public void test_getItemCount_ifDecoratedSectionIsEmpty_returnsOne() {
        Section section = new Section();
        AbstractViewCell emptyViewCell = new TestViewCell("EMPTY");
        EmptySectionDecorator emptySectionDecorator = new EmptySectionDecorator(section, emptyViewCell);

        int expectedItemCount = 1;
        int actualItemCount = emptySectionDecorator.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }

    @Test
    public void test_getItemCount_ifDecoratedSectionIsNotEmpty_returnCountOfDecoratedSection() {
        AbstractViewCell emptyViewCell = new TestViewCell("EMPTY");
        AbstractViewCell sectionItem1 = new TestViewCell("ITEM1");

        Section section = new Section();
        section.add(sectionItem1);

        EmptySectionDecorator emptySectionDecorator = new EmptySectionDecorator(section, emptyViewCell);

        int expectedItemCount = 1;
        int actualItemCount = emptySectionDecorator.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }

    @Test
    public void test_getZero_ifDecoratedSectionIsEmpty_returnsEmptyViewCell() {
        Section section = new Section();
        AbstractViewCell emptyViewCell = new TestViewCell("EMPTY");
        EmptySectionDecorator emptySectionDecorator = new EmptySectionDecorator(section, emptyViewCell);

        AbstractViewCell expectedViewCell = emptyViewCell;
        AbstractViewCell actualViewCell = emptySectionDecorator.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_getZero_ifDecoratedSectionIsNotEmpty_returnsFirstViewCell() {
        AbstractViewCell emptyViewCell = new TestViewCell("EMPTY");
        AbstractViewCell sectionItem1 = new TestViewCell("ITEM1");

        Section section = new Section();
        section.add(sectionItem1);

        EmptySectionDecorator emptySectionDecorator = new EmptySectionDecorator(section, emptyViewCell);

        AbstractViewCell expectedViewCell = sectionItem1;
        AbstractViewCell actualViewCell = emptySectionDecorator.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }
}
