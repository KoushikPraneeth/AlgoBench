package com.algobench.core;

/**
 * Abstract base class for all searching algorithm implementations.
 * Provides common functionality and metrics tracking for search operations.
 */
public abstract class SearchAlgorithm implements AlgorithmStrategy<SearchResult> {
    protected final AlgorithmMetrics metrics;

    protected SearchAlgorithm() {
        this.metrics = new AlgorithmMetrics();
    }

    @Override
    public SearchResult execute(SearchResult input) {
        if (input == null || input.getArray() == null || input.getArray().length == 0) {
            logger.warn("Empty or null input provided");
            return input;
        }

        metrics.reset();
        metrics.startTimer();
        metrics.updateMemoryUsage();

        int result = search(input.getArray(), input.getTarget());

        metrics.stopTimer();
        metrics.updateMemoryUsage();

        logger.info("Search completed: {}\nMetrics: {}", getName(), metrics);
        return new SearchResult(input.getArray(), input.getTarget(), result);
    }

    /**
     * Implements the specific search algorithm.
     * @param array The array to search in
     * @param target The value to search for
     * @return The index of the target value if found, -1 otherwise
     */
    protected abstract int search(int[] array, int target);

    @Override
    public AlgorithmMetrics getMetrics() {
        return metrics;
    }

    @Override
    public void reset() {
        metrics.reset();
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

/**
 * Wrapper class for search algorithm inputs and results.
 */
class SearchResult {
    private final int[] array;
    private final int target;
    private final int resultIndex;

    public SearchResult(int[] array, int target) {
        this(array, target, -1);
    }

    public SearchResult(int[] array, int target, int resultIndex) {
        this.array = array;
        this.target = target;
        this.resultIndex = resultIndex;
    }

    public int[] getArray() {
        return array;
    }

    public int getTarget() {
        return target;
    }

    public int getResultIndex() {
        return resultIndex;
    }

    public boolean isFound() {
        return resultIndex != -1;
    }
}