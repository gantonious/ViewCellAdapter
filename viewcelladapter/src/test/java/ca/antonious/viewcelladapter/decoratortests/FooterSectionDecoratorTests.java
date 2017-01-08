package ca.antonious.viewcelladapter.decoratortests;

import org.junit.Test;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.decorators.FooterSectionDecorator;
import ca.antonious.viewcelladapter.sections.AbstractSection;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.assertEquals;

/**
 * Created by George on 2017-01-01.
 */

public class FooterSectionDecoratorTests {
    @Test
    public void test_getZero_returnsFooter() {
        AbstractSection section = new Section();
        AbstractViewCell footerViewCell = new TestViewCell("FOOTER");
        FooterSectionDecorator footerSectionDecorator = new FooterSectionDecorator(section, footerViewCell);

        AbstractViewCell expectedViewCell = footerViewCell;
        AbstractViewCell actualViewCell = footerSectionDecorator.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_getOne_ifDecoratedSectionIsNotEmpty_thenReturnsSecondItemInSection() {
        AbstractViewCell sectionItem1 = new TestViewCell("ITEM1");
        AbstractViewCell sectionItem2 = new TestViewCell("ITEM2");
        AbstractViewCell footerViewCell = new TestViewCell("FOOTER");

        Section section = new Section();
        section.add(sectionItem1);
        section.add(sectionItem2);

        FooterSectionDecorator footerSectionDecorator = new FooterSectionDecorator(section, footerViewCell);

        AbstractViewCell expectedViewCell = sectionItem2;
        AbstractViewCell actualViewCell = footerSectionDecorator.get(1);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_getItemCount_ifDecoratedSectionIsEmptyAndShouldShowFooterIfEmpty_thenReturnsOne() {
        AbstractSection section = new Section();
        AbstractViewCell footerViewCell = new TestViewCell("FOOTER");

        FooterSectionDecorator footerSectionDecorator = new FooterSectionDecorator(section, footerViewCell);

        int expectedItemCount = 1;
        int actualItemCount = footerSectionDecorator.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }

    @Test
    public void test_getItemCount_ifDecoratedSectionIsEmptyAndShouldNotShowFooterIfEmpty_thenReturnsZero() {
        AbstractSection section = new Section();
        AbstractViewCell footerViewCell = new TestViewCell("FOOTER");

        FooterSectionDecorator footerSectionDecorator = new FooterSectionDecorator(section, footerViewCell);
        footerSectionDecorator.setShowFooterIfEmpty(false);

        int expectedItemCount = 0;
        int actualItemCount = footerSectionDecorator.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }
}
