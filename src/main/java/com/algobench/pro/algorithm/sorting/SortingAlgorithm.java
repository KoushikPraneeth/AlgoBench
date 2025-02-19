package com.algobench.pro.algorithm.sorting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code SortingAlgorithm} defines the base interface for all sorting algorithms in the AlgoBench Pro application.
 * Implementations of this interface must provide a method to sort an array of {@code Comparable} elements.
 *
 * <p><b>Type Parameter:</b></p>
 * <ul>
 *     <li>{@code <T>} - The type of elements to be sorted. Must implement {@code Comparable<T>}
 *     to ensure elements can be compared for sorting order.</li>
 * </ul>
 *
 * @param <T> the type of elements to be sorted, must implement Comparable
 * @version 1.0
 */
public interface SortingAlgorithm<T extends Comparable<T>> {
    Logger logger = LogManager.getLogger(SortingAlgorithm.class);

    /**
     * Sorts the given array in ascending order using the specific sorting algorithm implementation.
     *
     * @param array The array to be sorted in place.
     *              The element type {@code T} must implement {@code Comparable<T>} for proper sorting.
     * @throws IllegalArgumentException if the input array is null.
     */
    void sort(T[] array);

    /**
     * Gets the name of the sorting algorithm.
     * This name should be human-readable and suitable for display in the application's UI or reports.
     *
     * @return A string representing the name of the sorting algorithm.
     */
    String getName();

    /**
     * Validates the input array to ensure it is not null before proceeding with the sorting operation.
     *
     * @param array The array to validate.
     * @throws IllegalArgumentException If the array is {@code null}.
     */
    default void validateInput(T[] array) {
        if (array == null) {
            logger.error("Input array is null");
            throw new IllegalArgumentException("Input array cannot be null");
        }
        logger.debug("Input validation successful for array of length: {}", array.length);
    }
}
