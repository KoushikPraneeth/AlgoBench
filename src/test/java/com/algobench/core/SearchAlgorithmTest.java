package com.algobench.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SearchAlgorithmTest {
    private TestSearchAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        algorithm = new TestSearchAlgorithm();
    }

    @Test
    void testExecuteWithNullInput() {
        SearchResult result = algorithm.execute(null);
        assertNull(result);
    }

    @Test
    void testExecuteWithEmptyInput() {
        SearchResult input = new SearchResult(new int[0], 5);
        SearchResult result = algorithm.execute(input);
        assertEquals(input, result);
    }

    @Test
    void testExecuteWithValidInputFound() {
        int[] array = {1, 3, 5, 7, 9};
        SearchResult input = new SearchResult(array, 5);
        SearchResult result = algorithm.execute(input);
        
        assertTrue(result.isFound());
        assertEquals(2, result.getResultIndex());
    }

    @Test
    void testExecuteWithValidInputNotFound() {
        int[] array = {1, 3, 5, 7, 9};
        SearchResult input = new SearchResult(array, 4);
        SearchResult result = algorithm.execute(input);
        
        assertFalse(result.isFound());
        assertEquals(-1, result.getResultIndex());
    }

    @Test
    void testMetricsTracking() {
        int[] array = {1, 3, 5, 7, 9};
        SearchResult input = new SearchResult(array, 5);
        algorithm.execute(input);
        
        AlgorithmMetrics metrics = algorithm.getMetrics();
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getExecutionTimeNanos() > 0);
    }

    @Test
    void testReset() {
        int[] array = {1, 3, 5, 7, 9};
        SearchResult input = new SearchResult(array, 5);
        algorithm.execute(input);
        algorithm.reset();
        
        AlgorithmMetrics metrics = algorithm.getMetrics();
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getExecutionTimeNanos());
    }

    // Test implementation of SearchAlgorithm for testing purposes
    private static class TestSearchAlgorithm extends SearchAlgorithm {
        @Override
        protected int search(int[] array, int target) {
            // Simple linear search implementation for testing
            for (int i = 0; i < array.length; i++) {
                if (compare(array[i], target) == 0) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public String getName() {
            return "TestSearchAlgorithm";
        }
    }
}