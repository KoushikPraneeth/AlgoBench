package com.algobench.pro.algorithm.sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for QuickSort algorithm implementation.
 */
public class QuickSortTest {
    private QuickSort<Integer> quickSort;

    @BeforeEach
    void setUp() {
        quickSort = new QuickSort<>();
    }

    @Test
    @DisplayName("Test sorting with random integers")
    void testSortRandomArray() {
        Integer[] array = {64, 34, 25, 12, 22, 11, 90};
        Integer[] expected = {11, 12, 22, 25, 34, 64, 90};
        
        quickSort.sort(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test sorting with duplicate elements")
    void testSortWithDuplicates() {
        Integer[] array = {5, 2, 8, 5, 1, 9, 2, 8};
        Integer[] expected = {1, 2, 2, 5, 5, 8, 8, 9};
        
        quickSort.sort(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test sorting with already sorted array")
    void testSortSortedArray() {
        Integer[] array = {1, 2, 3, 4, 5};
        Integer[] expected = {1, 2, 3, 4, 5};
        
        quickSort.sort(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test sorting with reverse sorted array")
    void testSortReverseSortedArray() {
        Integer[] array = {5, 4, 3, 2, 1};
        Integer[] expected = {1, 2, 3, 4, 5};
        
        quickSort.sort(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test sorting with single element array")
    void testSortSingleElement() {
        Integer[] array = {1};
        Integer[] expected = {1};
        
        quickSort.sort(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test sorting with empty array")
    void testSortEmptyArray() {
        Integer[] array = {};
        Integer[] expected = {};
        
        quickSort.sort(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test sorting with null array throws exception")
    void testSortNullArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            quickSort.sort(null);
        });
    }

    @Test
    @DisplayName("Test algorithm name")
    void testGetName() {
        assertEquals("Quick Sort", quickSort.getName());
    }
}
