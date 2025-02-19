package com.algobench.pro.algorithm.searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BinarySearch algorithm implementation.
 */
public class BinarySearchTest {
    private BinarySearch<Integer> binarySearch;

    @BeforeEach
    void setUp() {
        binarySearch = new BinarySearch<>();
    }

    @Test
    @DisplayName("Test searching in sorted array - element present")
    void testSearchElementPresent() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int result = binarySearch.search(array, 7);
        assertEquals(6, result);
    }

    @Test
    @DisplayName("Test searching in sorted array - element not present")
    void testSearchElementNotPresent() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 8, 9, 10};
        int result = binarySearch.search(array, 7);
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Test searching in array with single element - element present")
    void testSearchSingleElementArrayPresent() {
        Integer[] array = {5};
        int result = binarySearch.search(array, 5);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Test searching in array with single element - element not present")
    void testSearchSingleElementArrayNotPresent() {
        Integer[] array = {5};
        int result = binarySearch.search(array, 3);
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Test searching in empty array")
    void testSearchEmptyArray() {
        Integer[] array = {};
        int result = binarySearch.search(array, 5);
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Test searching with null array throws exception")
    void testSearchNullArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.search(null, 5);
        });
    }

    @Test
    @DisplayName("Test searching with null target throws exception")
    void testSearchNullTarget() {
        Integer[] array = {1, 2, 3, 4, 5};
        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.search(array, null);
        });
    }

    @Test
    @DisplayName("Test searching in unsorted array throws exception")
    void testSearchUnsortedArray() {
        Integer[] array = {5, 2, 8, 1, 9};
        assertThrows(IllegalArgumentException.class, () -> {
            binarySearch.search(array, 5);
        });
    }

    @Test
    @DisplayName("Test algorithm name")
    void testGetName() {
        assertEquals("Binary Search", binarySearch.getName());
    }
}
