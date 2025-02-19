package com.algobench.core;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Provides a simple command-line menu interface for algorithm selection and execution.
 */
public class ConsoleMenu {
    private final Scanner scanner;
    private final List<AlgorithmStrategy<?>> algorithms;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.algorithms = new ArrayList<>();
    }

    public void registerAlgorithm(AlgorithmStrategy<?> algorithm) {
        algorithms.add(algorithm);
    }

    public void start() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            if (choice == 0) {
                System.out.println("Exiting AlgoBench Pro...");
                break;
            }

            if (choice > 0 && choice <= algorithms.size()) {
                executeAlgorithm(algorithms.get(choice - 1));
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== AlgoBench Pro ===\n");
        System.out.println("Available Algorithms:");
        for (int i = 0; i < algorithms.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, algorithms.get(i).getName());
        }
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }

    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    @SuppressWarnings("unchecked")
    private void executeAlgorithm(AlgorithmStrategy<?> algorithm) {
        System.out.println("\nExecuting " + algorithm.getName());
        
        // For now, we'll use a simple array for testing
        if (algorithm instanceof SortingAlgorithm) {
            int[] input = generateTestArray();
            System.out.println("Input array: " + arrayToString(input));
            
            SortingAlgorithm sorter = (SortingAlgorithm) algorithm;
            int[] result = sorter.execute(input);
            
            System.out.println("Sorted array: " + arrayToString(result));
        } else if (algorithm instanceof SearchAlgorithm) {
            int[] input = generateTestArray();
            System.out.println("Input array: " + arrayToString(input));
            System.out.print("Enter value to search for: ");
            int target = scanner.nextInt();
            
            SearchAlgorithm searcher = (SearchAlgorithm) algorithm;
            SearchResult result = searcher.execute(new SearchResult(input, target));
            
            if (result.isFound()) {
                System.out.println("Found at index: " + result.getResultIndex());
            } else {
                System.out.println("Value not found.");
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine(); // Consume the remaining newline
        scanner.nextLine(); // Wait for user input
    }

    private int[] generateTestArray() {
        System.out.print("Enter array size (max 20): ");
        int size = Math.min(20, scanner.nextInt());
        int[] array = new int[size];
        
        System.out.println("Enter " + size + " integers:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}