package com.algobench.pro.algorithm.tree;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@code BinarySearchTree} class provides an implementation of a Binary Search Tree (BST) data structure.
 * <p>
 * A BST is a node-based binary tree data structure which has the following properties:
 * <ul>
 *     <li>The left subtree of a node contains only nodes with values less than the node's value.</li>
 *     <li>The right subtree of a node contains only nodes with values greater than the node's value.</li>
 *     <li>The left and right subtree must also be binary search trees.</li>
 * </ul>
 * <p>
 * This implementation supports insertion and searching of elements, and is generic to support any {@code Comparable} type.
 *
 * <p><b>Type Parameter:</b></p>
 * <ul>
 *     <li>{@code <T>} - The type of elements stored in the BST. Must implement {@code Comparable<T>}
 *     to allow for value comparisons and proper BST ordering.</li>
 * </ul>
 *
 * @param <T> the type of elements stored in the BST, must be comparable
 * @version 1.0
 */
public class BinarySearchTree<T extends Comparable<T>> {
    private static final Logger logger = LogManager.getLogger(BinarySearchTree.class);
    private Node<T> root;
    private int size;

    /**
     * Constructs an empty BinarySearchTree.
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
        logger.debug("BinarySearchTree initialized");
    }

    /**
     * Inserts a new value into the Binary Search Tree, maintaining the BST properties.
     * <p>
     * If the value is null, it will not be inserted. Duplicate values are ignored.
     *
     * @param value The value to insert into the BST. Must not be null.
     */
    public void insert(T value) {
        if (value == null) {
            logger.warn("Cannot insert null value");
            return; // Do not insert null values
        }
        root = insertRecursive(root, value);
        size++;
        logger.debug("Value {} inserted into BST", value);
    }

    private Node<T> insertRecursive(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }

        int compareResult = value.compareTo(current.value);

        if (compareResult < 0) {
            current.left = insertRecursive(current.left, value);
        } else if (compareResult > 0) {
            current.right = insertRecursive(current.right, value);
        } else {
            // Value already exists, do not insert duplicate
            logger.debug("Duplicate value {} not inserted", value);
            return current;
        }

        return current;
    }

    /**
     * Checks if the BST contains a specific value.
     *
     * @param value The value to search for.
     * @return {@code true} if the value is found in the BST, {@code false} otherwise.
     */
    public boolean contains(T value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node<T> current, T value) {
        if (current == null) {
            return false;
        }

        int compareResult = value.compareTo(current.value);

        if (compareResult == 0) {
            return true;
        }

        return compareResult < 0
            ? containsRecursive(current.left, value)
            : containsRecursive(current.right, value);
    }

    /**
     * Returns the number of nodes in the Binary Search Tree.
     *
     * @return The size of the BST.
     */
    public int size() {
        return size;
    }

    /**
     * Gets the root node of this BinarySearchTree.
     *
     * @return The root node of the BST, or {@code null} if the tree is empty.
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * {@code Node} class is an inner class representing a node within the Binary Search Tree.
     * <p>
     * Each node holds a value of type {@code T} and references to its left and right children.
     *
     * @param <T> The type of value stored in this node.
     */
    public static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        /**
         * Constructs a new node with the specified value.
         *
         * @param value The value to be stored in this node.
         */
        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
}
