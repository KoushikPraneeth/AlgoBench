package com.algobench.core;

import java.util.List;

/**
 * Abstract base class for all graph algorithm implementations.
 * Provides common functionality and metrics tracking for graph operations.
 */
public abstract class GraphAlgorithm<T> implements AlgorithmStrategy<T> {
    protected final AlgorithmMetrics metrics;

    protected GraphAlgorithm() {
        this.metrics = new AlgorithmMetrics();
    }

    @Override
    public T execute(T input) {
        if (input == null) {
            logger.warn("Null input provided");
            return null;
        }

        metrics.reset();
        metrics.startTimer();
        metrics.updateMemoryUsage();

        T result = processGraph(input);

        metrics.stopTimer();
        metrics.updateMemoryUsage();

        logger.info("Graph algorithm completed: {}\nMetrics: {}", getName(), metrics);
        return result;
    }

    /**
     * Implements the specific graph algorithm.
     * @param input The input graph data structure
     * @return The result of the graph algorithm
     */
    protected abstract T processGraph(T input);

    @Override
    public AlgorithmMetrics getMetrics() {
        return metrics;
    }

    @Override
    public void reset() {
        metrics.reset();
    }

    /**
     * Records a node visit in the metrics.
     */
    protected void recordNodeVisit() {
        metrics.incrementComparisons();
    }

    /**
     * Records an edge traversal in the metrics.
     */
    protected void recordEdgeTraversal() {
        metrics.incrementSwaps(); // Reusing swaps counter for edge traversals
    }
}