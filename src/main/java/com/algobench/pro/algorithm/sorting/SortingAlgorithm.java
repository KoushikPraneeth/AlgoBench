package com.algobench.pro.algorithm.sorting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base interface for all sorting algorithms.
 * @param <T> The type of elements to be sorted
 */
public interface SortingAlgorithm<T extends Comparable<T>> {
    Logger logger = LogManager.getLogger(SortingAlgorithm.class);

    /**
     * Sorts the given array in ascending order.
     * @param array The array to be sorted
     */
    void sort(T[] array);

    /**
     * Returns the name of the sorting algorithm.
     * @return The algorithm name
     */
    String getName();

    /**
     * Validates the input array before sorting.
     * @param array The array to validate
     * @throws IllegalArgumentException if the array is null
     */
    default void validateInput(T[] array) {
        if (array == null) {
            logger.error("Input array is null");
            throw new IllegalArgumentException("Input array cannot be null");
        }
        logger.debug("Input validation successful for array of length: {}", array.length);
    }
}
