package ca.antonious.viewcelladapter.sectiontests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.*;
/**
 * Created by George on 2016-12-18.
 */

public class SectionTests {
    @Test
    public void test_add_addsViewCellToSection() {
        AbstractViewCell viewCell = new TestViewCell("ITEM-1");

        Section section = new Section();
        section.add(viewCell);

        List<AbstractViewCell> expectedViewCells = Arrays.asList(viewCell);
        List<AbstractViewCell> actualViewCells = section.getAll();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_addAll_addsAllViewCellsToSection() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");

        Section section = new Section();
        section.addAll(Arrays.asList(viewCell1, viewCell2));

        List<AbstractViewCell> expectedViewCells = Arrays.asList(viewCell1, viewCell2);
        List<AbstractViewCell> actualViewCells = section.getAll();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_setAll_addsAllViewCellsToSection() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");
        AbstractViewCell viewCell3 = new TestViewCell("ITEM-3");

        Section section = new Section();
        section.add(viewCell1);
        section.setAll(Arrays.asList(viewCell2, viewCell3));

        List<AbstractViewCell> expectedViewCells = Arrays.asList(viewCell2, viewCell3);
        List<AbstractViewCell> actualViewCells = section.getAll();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_prependAll_prependsAllViewCellsToSection() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");
        AbstractViewCell viewCell3 = new TestViewCell("ITEM-3");

        Section section = new Section();
        section.add(viewCell1);
        section.prependAll(Arrays.asList(viewCell2, viewCell3));

        List<AbstractViewCell> expectedViewCells = Arrays.asList(viewCell2, viewCell3, viewCell1);
        List<AbstractViewCell> actualViewCells = section.getAll();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_clear_clearsAllViewCells() {
        AbstractViewCell viewCell = new TestViewCell("ITEM-1");

        Section section = new Section();
        section.add(viewCell);
        section.clear();

        List<AbstractViewCell> expectedViewCells = new ArrayList<>();
        List<AbstractViewCell> actualViewCells = section.getAll();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_remove_removesViewCellAtSpecifiedIndex() {
        AbstractViewCell viewCell1 = new TestViewCell("ITEM-1");
        AbstractViewCell viewCell2 = new TestViewCell("ITEM-2");
        AbstractViewCell viewCell3 = new TestViewCell("ITEM-3");

        Section section = new Section();
        section.setAll(Arrays.asList(viewCell1, viewCell2, viewCell3));
        section.remove(1);

        List<AbstractViewCell> expectedViewCells = Arrays.asList(viewCell1, viewCell3);
        List<AbstractViewCell> actualViewCells = section.getAll();

        assertEquals(expectedViewCells, actualViewCells);
    }
}
