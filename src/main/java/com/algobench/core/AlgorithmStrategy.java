package com.algobench.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base interface for all algorithm implementations in AlgoBench Pro.
 * Defines the core contract that all algorithms must fulfill.
 */
public interface AlgorithmStrategy<T> {
    Logger logger = LoggerFactory.getLogger(AlgorithmStrategy.class);
    
    /**
     * Executes the algorithm with the given input.
     * @param input The input data to process
     * @return The result of the algorithm execution
     */
    T execute(T input);
    
    /**
     * Gets the name of the algorithm.
     * @return The algorithm name
     */
    String getName();
    
    /**
     * Gets the current metrics for the algorithm execution.
     * @return The current algorithm metrics
     */
    AlgorithmMetrics getMetrics();
    
    /**
     * Resets the algorithm's state and metrics.
     */
    void reset();
}