package ca.antonious.viewcelladapter.decoratortests;

import org.junit.Test;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.decorators.HeaderSectionDecorator;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-18.
 */

public class HeaderSectionDecoratorTests {

    @Test
    public void test_getZero_returnsHeader() {
        AbstractSection section = new Section();
        AbstractViewCell headerViewCell = new TestViewCell("HEADER");
        HeaderSectionDecorator headerSectionDecorator = new HeaderSectionDecorator(section, headerViewCell);

        AbstractViewCell expectedViewCell = headerViewCell;
        AbstractViewCell actualViewCell = headerSectionDecorator.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_getZero_ifHeaderIsNotVisible_thenReturnsFirstItemInSection() {
        AbstractViewCell headerViewCell = new TestViewCell("HEADER");
        AbstractViewCell sectionItem1 = new TestViewCell("ITEM1");

        Section section = new Section();
        section.add(sectionItem1);

        HeaderSectionDecorator headerSectionDecorator = new HeaderSectionDecorator(section, headerViewCell);
        headerSectionDecorator.setIsHeaderVisible(false);

        AbstractViewCell expectedViewCell = sectionItem1;
        AbstractViewCell actualViewCell = headerSectionDecorator.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_getOne_ifDecoratedSectionIsNotEmpty_thenReturnsFirstItemInSection() {
        AbstractViewCell headerViewCell = new TestViewCell("HEADER");
        AbstractViewCell sectionItem1 = new TestViewCell("ITEM1");

        Section section = new Section();
        section.add(sectionItem1);

        HeaderSectionDecorator headerSectionDecorator = new HeaderSectionDecorator(section, headerViewCell);

        AbstractViewCell expectedViewCell = sectionItem1;
        AbstractViewCell actualViewCell = headerSectionDecorator.get(1);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_getItemCount_ifDecoratedSectionIsEmptyAndShouldShowHeaderIfEmpty_thenReturnsOne() {
        AbstractSection section = new Section();
        AbstractViewCell headerViewCell = new TestViewCell("HEADER");
        HeaderSectionDecorator headerSectionDecorator = new HeaderSectionDecorator(section, headerViewCell);

        int expectedItemCount = 1;
        int actualItemCount = headerSectionDecorator.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }

    @Test
    public void test_getItemCount_ifDecoratedSectionIsEmptyAndShouldNotShowHeaderIfEmpty_thenReturnsZero() {
        AbstractSection section = new Section();
        AbstractViewCell headerViewCell = new TestViewCell("HEADER");
        HeaderSectionDecorator headerSectionDecorator = new HeaderSectionDecorator(section, headerViewCell);
        headerSectionDecorator.setShowHeaderIfEmpty(false);

        int expectedItemCount = 0;
        int actualItemCount = headerSectionDecorator.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }
}
