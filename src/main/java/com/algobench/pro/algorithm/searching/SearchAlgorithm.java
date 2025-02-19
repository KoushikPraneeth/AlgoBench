package com.algobench.pro.algorithm.searching;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code SearchAlgorithm} serves as the base interface for all search algorithms within the AlgoBench Pro application.
 * Implementations of this interface are expected to provide functionality for searching for a target element
 * within an array of elements.
 *
 * <p><b>Type Parameter:</b></p>
 * <ul>
 *     <li>{@code <T>} - The type of elements within the search domain.</li>
 * </ul>
 *
 * @param <T> the type of elements to search through
 * @version 1.0
 */
public interface SearchAlgorithm<T> {
    Logger logger = LogManager.getLogger(SearchAlgorithm.class);

    /**
     * Searches for the target element within the provided array.
     *
     * @param array  The array to be searched.
     * @param target The element being searched for.
     * @return The index of the target element if it is found within the array, otherwise -1.
     * @throws IllegalArgumentException if the input array is null.
     */
    int search(T[] array, T target);

    /**
     * Returns the name of the search algorithm.
     * This name is intended to be human-readable and used in the application's UI or reporting features.
     *
     * @return A string representing the name of the search algorithm implementation.
     */
    String getName();

    /**
     * Validates the input array to ensure it is not null before proceeding with the search operation.
     *
     * @param array The array to be validated.
     * @throws IllegalArgumentException If the array is {@code null}.
     */
    default void validateArray(T[] array) {
        if (array == null) {
            logger.error("Input array is null");
            throw new IllegalArgumentException("Input array cannot be null");
        }
        logger.debug("Input validation successful for array of length: {}", array.length);
    }
}
