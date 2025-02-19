package com.algobench.pro.benchmark.runner;

import com.algobench.pro.algorithm.searching.SearchAlgorithm;
import com.algobench.pro.algorithm.sorting.SortingAlgorithm;
import com.algobench.pro.benchmark.BenchmarkConfig;
import com.algobench.pro.benchmark.metrics.MemoryTracker;
import com.algobench.pro.benchmark.metrics.PerformanceTimer;
import com.algobench.pro.benchmark.results.BenchmarkResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Runs benchmarks for algorithms with different input sizes.
 */
public class BenchmarkRunner {
    private static final Logger logger = LogManager.getLogger(BenchmarkRunner.class);
    private final BenchmarkConfig config;
    private final PerformanceTimer timer;
    private final MemoryTracker memoryTracker;
    private final Random random;

    public BenchmarkRunner() {
        this(new BenchmarkConfig());
    }

    public BenchmarkRunner(BenchmarkConfig config) {
        this.config = config;
        this.timer = new PerformanceTimer();
        this.memoryTracker = new MemoryTracker();
        this.random = new Random();
    }

    /**
     * Benchmarks a sorting algorithm with different array sizes.
     */
    public BenchmarkResult benchmarkSorting(SortingAlgorithm<Integer> algorithm) {
        logger.info("Starting benchmark for {}", algorithm.getName());
        BenchmarkResult result = new BenchmarkResult(
            algorithm.getName(),
            config.getWarmupRuns(),
            config.getMeasurementRuns()
        );

        for (int size : config.getInputSizes()) {
            runSortingBenchmark(algorithm, size, result);
        }

        logger.info("Benchmark completed for {}", algorithm.getName());
        return result;
    }

    /**
     * Benchmarks a searching algorithm with different array sizes.
     */
    public BenchmarkResult benchmarkSearching(SearchAlgorithm<Integer> algorithm) {
        logger.info("Starting benchmark for {}", algorithm.getName());
        BenchmarkResult result = new BenchmarkResult(
            algorithm.getName(),
            config.getWarmupRuns(),
            config.getMeasurementRuns()
        );

        for (int size : config.getInputSizes()) {
            runSearchingBenchmark(algorithm, size, result);
        }

        logger.info("Benchmark completed for {}", algorithm.getName());
        return result;
    }

    private void runSortingBenchmark(SortingAlgorithm<Integer> algorithm, int size, BenchmarkResult result) {
        logger.info("Running sorting benchmark for size: {}", size);
        
        // Run warmup phase
        for (int i = 0; i < config.getWarmupRuns(); i++) {
            Integer[] array = generateRandomArray(size);
            runSingleSortTest(algorithm, array, result, size);
        }

        // Run measurement phase
        memoryTracker.snapshot(); // Take baseline memory snapshot
        for (int i = 0; i < config.getMeasurementRuns(); i++) {
            Integer[] array = generateRandomArray(size);
            runSingleSortTest(algorithm, array, result, size);
        }
        result.setMemoryUsage(size, memoryTracker.getMemoryDelta());

        logger.info("Completed benchmark for size: {}", size);
    }

    private void runSearchingBenchmark(SearchAlgorithm<Integer> algorithm, int size, BenchmarkResult result) {
        logger.info("Running searching benchmark for size: {}", size);
        
        // Create sorted array for binary search
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        // Run warmup phase
        for (int i = 0; i < config.getWarmupRuns(); i++) {
            int target = random.nextInt(size);
            runSingleSearchTest(algorithm, array, target, result, size);
        }

        // Run measurement phase
        memoryTracker.snapshot(); // Take baseline memory snapshot
        for (int i = 0; i < config.getMeasurementRuns(); i++) {
            int target = random.nextInt(size);
            runSingleSearchTest(algorithm, array, target, result, size);
        }
        result.setMemoryUsage(size, memoryTracker.getMemoryDelta());

        logger.info("Completed benchmark for size: {}", size);
    }

    private void runSingleSortTest(SortingAlgorithm<Integer> algorithm, Integer[] array, 
                                 BenchmarkResult result, int size) {
        if (config.isGcBeforeBenchmark()) {
            memoryTracker.gcAndWait();
        }

        timer.start();
        algorithm.sort(array);
        Duration duration = timer.stop();
        
        result.addTiming(size, duration);
        logger.debug("Sort completed in {} ms", duration.toMillis());
    }

    private void runSingleSearchTest(SearchAlgorithm<Integer> algorithm, Integer[] array, 
                                   int target, BenchmarkResult result, int size) {
        if (config.isGcBeforeBenchmark()) {
            memoryTracker.gcAndWait();
        }

        timer.start();
        algorithm.search(array, target);
        Duration duration = timer.stop();
        
        result.addTiming(size, duration);
        logger.debug("Search completed in {} ms", duration.toMillis());
    }

    private Integer[] generateRandomArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000); // Random numbers between 0 and 9999
        }
        return array;
    }
}
