package com.algobench.pro.algorithm.searching;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base interface for all search algorithms.
 * @param <T> The type of elements to search through
 */
public interface SearchAlgorithm<T> {
    Logger logger = LogManager.getLogger(SearchAlgorithm.class);

    /**
     * Searches for the target element in the given array.
     * @param array The array to search in
     * @param target The element to search for
     * @return The index of the target element if found, -1 otherwise
     */
    int search(T[] array, T target);

    /**
     * Returns the name of the search algorithm.
     * @return The algorithm name
     */
    String getName();

    /**
     * Validates the input array before searching.
     * @param array The array to validate
     * @throws IllegalArgumentException if the array is null
     */
    default void validateArray(T[] array) {
        if (array == null) {
            logger.error("Input array is null");
            throw new IllegalArgumentException("Input array cannot be null");
        }
        logger.debug("Input validation successful for array of length: {}", array.length);
    }
}
