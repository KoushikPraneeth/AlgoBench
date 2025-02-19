package com.algobench.pro.algorithm.searching;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements the Binary Search algorithm for searching in sorted arrays.
 * <p>
 * Binary search is an efficient algorithm for finding a target value within a sorted array.
 * It works by repeatedly dividing in half the portion of the array that could contain the target value,
 * comparing the target value to the middle element of the search interval, and narrowing down the search
 * based on the comparison.
 * <p>
 * <b>Note:</b> This algorithm requires the input array to be sorted in ascending order for correct operation.
 *
 * @param <T> the type of elements in the array and the target value, must implement Comparable
 * @version 1.0
 * @see SearchAlgorithm
 */
public class BinarySearch<T extends Comparable<T>> implements SearchAlgorithm<T> {
    private static final Logger logger = LogManager.getLogger(BinarySearch.class);

    @Override
    public int search(T[] array, T target) {
        validateArray(array);
        if (target == null) {
            logger.error("Search target is null");
            throw new IllegalArgumentException("Search target cannot be null");
        }

        logger.info("Starting binary search for target: {} in array of length: {}", target, array.length);
        int result = binarySearch(array, target, 0, array.length - 1);
        
        if (result != -1) {
            logger.info("Target found at index: {}", result);
        } else {
            logger.info("Target not found in array");
        }
        
        return result;
    }

    @Override
    public String getName() {
        return "Binary Search";
    }

    private int binarySearch(T[] array, T target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            logger.debug("Searching in range [{}, {}], mid index: {}", left, right, mid);

            int comparison = target.compareTo(array[mid]);
            
            if (comparison == 0) {
                logger.debug("Target found at index: {}", mid);
                return mid;
            }

            if (comparison < 0) {
                logger.debug("Target is smaller than element at index {}, searching left half", mid);
                right = mid - 1;
            } else {
                logger.debug("Target is larger than element at index {}, searching right half", mid);
                left = mid + 1;
            }
        }

        logger.debug("Target not found in array");
        return -1;
    }

    /**
     * Additional validation to ensure the array is sorted.
     * @param array The array to validate
     * @throws IllegalArgumentException if the array is not sorted
     */
    @Override
    public void validateArray(T[] array) {
        SearchAlgorithm.super.validateArray(array);
        
        if (array.length > 0) {
            // Check if array is sorted
            for (int i = 1; i < array.length; i++) {
                if (array[i-1].compareTo(array[i]) > 0) {
                    logger.error("Input array is not sorted at index: {}", i);
                    throw new IllegalArgumentException("Binary search requires a sorted array");
                }
            }
            logger.debug("Array validated as sorted");
        }
    }
}
