package com.algobench.pro;

import com.algobench.pro.algorithm.sorting.QuickSort;
import com.algobench.pro.algorithm.sorting.SortingAlgorithm;
import com.algobench.pro.algorithm.searching.BinarySearch;
import com.algobench.pro.algorithm.searching.SearchAlgorithm;
import com.algobench.pro.benchmark.BenchmarkConfig;
import com.algobench.pro.benchmark.runner.BenchmarkRunner;
import com.algobench.pro.benchmark.results.BenchmarkResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance test suite to benchmark various algorithms.
 */
public class PerformanceTestSuite {

    @Test
    @DisplayName("Benchmark QuickSort Algorithm Performance")
    void benchmarkQuickSortPerformance() {
        SortingAlgorithm<Integer> quickSort = new QuickSort<>();
        BenchmarkRunner runner = new BenchmarkRunner(new BenchmarkConfig());
        BenchmarkResult result = runner.benchmarkSorting(quickSort);

        assertNotNull(result, "Benchmark result should not be null");
        assertEquals("Quick Sort", result.getAlgorithmName(), "Algorithm name should match");
        assertFalse(result.getInputSizes().isEmpty(), "Input sizes should not be empty");

        result.getInputSizes().forEach(size -> {
            assertNotNull(result.getStatistics(size), "Statistics should exist for each input size");
            assertTrue(result.getStatistics(size).getAverage().toMillis() > 0, "Average time should be positive");
            assertTrue(result.getMemoryUsage().get(size) >= 0, "Memory usage should not be negative");
        });

        System.out.println("\nQuickSort Benchmark Performance Test Result:");
        System.out.println(result.generateReport());
    }

    @Test
    @DisplayName("Benchmark BinarySearch Algorithm Performance")
    void benchmarkBinarySearchPerformance() {
        SearchAlgorithm<Integer> binarySearch = new BinarySearch<>();
        BenchmarkRunner runner = new BenchmarkRunner(new BenchmarkConfig());
        BenchmarkResult result = runner.benchmarkSearching(binarySearch);

        assertNotNull(result, "Benchmark result should not be null");
        assertEquals("Binary Search", result.getAlgorithmName(), "Algorithm name should match");
        assertFalse(result.getInputSizes().isEmpty(), "Input sizes should not be empty");

        result.getInputSizes().forEach(size -> {
            assertNotNull(result.getStatistics(size), "Statistics should exist for each input size");
            assertTrue(result.getStatistics(size).getAverage().toMillis() > 0, "Average time should be positive");
            assertTrue(result.getMemoryUsage().get(size) >= 0, "Memory usage should not be negative");
        });

        System.out.println("\nBinarySearch Benchmark Performance Test Result:");
        System.out.println(result.generateReport());
    }
}
