package ca.antonious.viewcelladapter.sectiontests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ca.antonious.viewcelladapter.Function;
import ca.antonious.viewcelladapter.TestViewCell;
import ca.antonious.viewcelladapter.sections.HomogeneousSection;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;

import static org.junit.Assert.assertEquals;

/**
 * Created by George on 2016-12-19.
 */

public class HomogeneousSectionTests {
    private Function<String, TestViewCell> testViewCellFactory;

    @Before
    public void set_up() {
        testViewCellFactory = new Function<String, TestViewCell>() {
            @Override
            public TestViewCell apply(String s) {
                return new TestViewCell(s);
            }
        };
    }

    @Test
    public void test_add_addsViewCellToSection() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");

        List<String> expectedModels = Arrays.asList("ITEM-1");
        List<String> actualModels = section.getAllModels();

        assertEquals(expectedModels, actualModels);
    }

    @Test
    public void test_ifFilterAndSortIsNotSet_thenReturnsAllViewCells() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");

        List<TestViewCell> expectedViewCells = Arrays.asList(new TestViewCell("ITEM-1"), new TestViewCell("ITEM-2"));
        List<TestViewCell> actualViewCells = section.getAllViewCells();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_filter_ifFilterIsSet_thenReturnsOnlyFilteredViewCells() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");

        section.setFilterFunction(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String input) {
                return input.equals("ITEM-2");
            }
        });

        List<TestViewCell> expectedViewCells = Arrays.asList(new TestViewCell("ITEM-2"));
        List<TestViewCell> actualViewCells = section.getAllViewCells();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_filter_getItemCount_shouldReturnFilteredCount() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");

        section.setFilterFunction(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return input.equals("ITEM-2");
            }
        });

        int expectedItemCount = 1;
        int actualItemCount = section.getItemCount();

        assertEquals(expectedItemCount, actualItemCount);
    }

    @Test
    public void test_filter_get_shouldReturnOnlyFilteredItem() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");

        section.setFilterFunction(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return input.equals("ITEM-2");
            }
        });

        AbstractViewCell expectedViewCell = new TestViewCell("ITEM-2");
        AbstractViewCell actualViewCell = section.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_filter_remove_shouldRemoveFilteredItem() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");
        section.add("ITEM-3");

        section.setFilterFunction(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return input.equals("ITEM-2") || input.equals("ITEM-3");
            }
        });

        section.remove(0);

        AbstractViewCell expectedViewCell = new TestViewCell("ITEM-3");
        AbstractViewCell actualViewCell = section.get(0);

        assertEquals(expectedViewCell, actualViewCell);
    }

    @Test
    public void test_sort_ifComparatorIsSet_thenReturnsViewCellsInExpectedSortedOrder() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");

        section.setModelComparator(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });

        List<TestViewCell> expectedViewCells = Arrays.asList(new TestViewCell("ITEM-2"), new TestViewCell("ITEM-1"));
        List<TestViewCell> actualViewCells = section.getAllViewCells();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_filter_and_sort_returnsExpectedViewCells() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");
        section.add("ITEM-3");

        section.setModelComparator(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });

        section.setFilterFunction(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return !input.equals("ITEM-2");
            }
        });

        List<TestViewCell> expectedViewCells = Arrays.asList(new TestViewCell("ITEM-3"), new TestViewCell("ITEM-1"));
        List<TestViewCell> actualViewCells = section.getAllViewCells();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_getAllSelectedModels_ifSelectedModelShouldNotBeRendered_thenItIsNotReturnedAsSelected() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");
        section.add("ITEM-3");

        section.get(0).select();

        section.setFilterFunction(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return input.equals("ITEM-2") || input.equals("ITEM-3");
            }
        });

        List<String> expectedViewCells = new ArrayList<>();
        List<String> actualViewCells = section.getAllSelectedModels();

        assertEquals(expectedViewCells, actualViewCells);
    }

    @Test
    public void test_getAllSelectedModels_ifSelectedModelShouldBeRendered_thenItIsReturnedAsSelected() {
        HomogeneousSection<String, TestViewCell> section = new HomogeneousSection<>(testViewCellFactory);
        section.add("ITEM-1");
        section.add("ITEM-2");
        section.add("ITEM-3");

        section.get(0).select();

        section.setFilterFunction(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return input.equals("ITEM-1") || input.equals("ITEM-3");
            }
        });

        List<String> expectedViewCells = Arrays.asList("ITEM-1");
        List<String> actualViewCells = section.getAllSelectedModels();

        assertEquals(expectedViewCells, actualViewCells);
    }
}
