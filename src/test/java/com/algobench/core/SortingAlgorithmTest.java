package com.algobench.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SortingAlgorithmTest {
    private TestSortingAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        algorithm = new TestSortingAlgorithm();
    }

    @Test
    void testExecuteWithNullInput() {
        int[] result = algorithm.execute(null);
        assertNull(result);
    }

    @Test
    void testExecuteWithEmptyInput() {
        int[] input = new int[0];
        int[] result = algorithm.execute(input);
        assertArrayEquals(input, result);
    }

    @Test
    void testExecuteWithValidInput() {
        int[] input = {3, 1, 4, 1, 5};
        int[] result = algorithm.execute(input);
        assertArrayEquals(new int[]{1, 1, 3, 4, 5}, result);
    }

    @Test
    void testMetricsTracking() {
        int[] input = {3, 1, 4};
        algorithm.execute(input);
        
        AlgorithmMetrics metrics = algorithm.getMetrics();
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getSwaps() > 0);
        assertTrue(metrics.getExecutionTimeNanos() > 0);
    }

    @Test
    void testReset() {
        int[] input = {3, 1, 4};
        algorithm.execute(input);
        algorithm.reset();
        
        AlgorithmMetrics metrics = algorithm.getMetrics();
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getExecutionTimeNanos());
    }

    // Test implementation of SortingAlgorithm for testing purposes
    private static class TestSortingAlgorithm extends SortingAlgorithm {
        @Override
        protected int[] sort(int[] array) {
            // Simple bubble sort implementation for testing
            for (int i = 0; i < array.length - 1; i++) {
                for (int j = 0; j < array.length - i - 1; j++) {
                    if (compare(array[j], array[j + 1]) > 0) {
                        swap(array, j, j + 1);
                    }
                }
            }
            return array;
        }

        @Override
        public String getName() {
            return "TestSortingAlgorithm";
        }
    }
}