package ca.antonious.viewcelladapter.sectiontests;

import org.junit.Test;

import java.util.Iterator;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-30.
 */

public class AbstractSectionIteratorTests {
    @Test
    public void test_iteration_ifSectionIsEmpty_hasNextStartsAsFalse() {
        Section section = new Section();
        assertFalse(section.iterator().hasNext());
    }

    @Test
    public void test_iteration_ifSectionIsNotEmpty_thenIteratesInTheCorrectOrder() {
        TestViewCell testViewCell1 = new TestViewCell("ITEM-1");
        TestViewCell testViewCell2 = new TestViewCell("ITEM-2");

        Section section = new Section();
        section.add(testViewCell1);
        section.add(testViewCell2);

        Iterator<AbstractViewCell> sectionIterator = section.iterator();

        AbstractViewCell viewCell1 = sectionIterator.next();
        AbstractViewCell viewCell2 = sectionIterator.next();
        boolean hasNextAfterCompletingIteration = sectionIterator.hasNext();

        assertEquals(viewCell1, testViewCell1);
        assertEquals(viewCell2, testViewCell2);
        assertFalse(hasNextAfterCompletingIteration);
    }
}
