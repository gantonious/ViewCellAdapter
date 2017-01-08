package ca.antonious.viewcelladapter.utilstests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.antonious.viewcelladapter.utils.CollectionUtils;

import static org.junit.Assert.*;

/**
 * Created by George on 2016-12-30.
 */

public class CollectionUtilsTests {
    @Test
    public void test_setAll_setsAllItemsInList() {
        List<String> baseList = new ArrayList<>();
        baseList.add("Test-1");
        baseList.add("Test-2");

        List<String> expectedList = Arrays.asList("Test-2", "Test-3");

        CollectionUtils.setAll(baseList, Arrays.asList("Test-2", "Test-3"));

        assertEquals(expectedList, baseList);
    }

    @Test
    public void test_prependAll_addsItemsToTheStartOfTheList() {
        List<String> baseList = new ArrayList<>();
        baseList.add("Test-1");
        baseList.add("Test-2");

        List<String> expectedList = Arrays.asList("Test-2", "Test-3", "Test-1", "Test-2");

        CollectionUtils.prependAll(baseList, Arrays.asList("Test-2", "Test-3"));

        assertEquals(expectedList, baseList);
    }
}
