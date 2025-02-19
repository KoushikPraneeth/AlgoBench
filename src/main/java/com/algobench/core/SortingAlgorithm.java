package com.algobench.core;

/**
 * Abstract base class for all sorting algorithm implementations.
 * Provides common functionality and metrics tracking for sorting operations.
 */
public abstract class SortingAlgorithm implements AlgorithmStrategy<int[]> {
    protected final AlgorithmMetrics metrics;

    protected SortingAlgorithm() {
        this.metrics = new AlgorithmMetrics();
    }

    @Override
    public int[] execute(int[] input) {
        if (input == null || input.length == 0) {
            logger.warn("Empty or null input array provided");
            return input;
        }

        metrics.reset();
        metrics.startTimer();
        metrics.updateMemoryUsage();

        int[] result = sort(input.clone());

        metrics.stopTimer();
        metrics.updateMemoryUsage();

        logger.info("Sorting completed: {}\nMetrics: {}", getName(), metrics);
        return result;
    }

    /**
     * Implements the specific sorting algorithm.
     * @param array The array to be sorted
     * @return The sorted array
     */
    protected abstract int[] sort(int[] array);

    @Override
    public AlgorithmMetrics getMetrics() {
        return metrics;
    }

    @Override
    public void reset() {
        metrics.reset();
    }

    /**
     * Swaps two elements in the array and updates metrics.
     * @param array The array containing elements to swap
     * @param i Index of first element
     * @param j Index of second element
     */
    protected void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        metrics.incrementSwaps();
    }

    /**
     * Compares two elements and updates metrics.
     * @param a First element
     * @param b Second element
     * @return Negative if a < b, zero if a == b, positive if a > b
     */
    protected int compare(int a, int b) {
        metrics.incrementComparisons();
        return Integer.compare(a, b);
    }
}