package com.algobench.pro.ui.console;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Handles user input with type-safe parsing and validation.
 */
public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Gets valid input from the user with type parsing and validation.
     *
     * @param prompt The prompt to display to the user
     * @param parser Function to parse string input to desired type
     * @param validator Predicate to validate the parsed input
     * @param <T> The type of value to return
     * @return The parsed and validated input
     */
    public <T> T getValidInput(String prompt, Function<String, T> parser, Predicate<T> validator) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                T value = parser.apply(input);
                if (validator.test(value)) {
                    return value;
                }
                System.out.println("Invalid input. Please try again.");
            } catch (Exception e) {
                System.out.println("Invalid format. Please try again.");
            }
        }
    }

    /**
     * Gets a valid integer input within a specified range.
     *
     * @param prompt The prompt to display to the user
     * @param min Minimum acceptable value (inclusive)
     * @param max Maximum acceptable value (inclusive)
     * @return The validated integer input
     */
    public int getIntInRange(String prompt, int min, int max) {
        return getValidInput(
            prompt,
            Integer::parseInt,
            value -> value >= min && value <= max
        );
    }

    /**
     * Gets a valid positive integer input.
     *
     * @param prompt The prompt to display to the user
     * @return The validated positive integer
     */
    public int getPositiveInt(String prompt) {
        return getValidInput(
            prompt,
            Integer::parseInt,
            value -> value > 0
        );
    }

    /**
     * Closes the scanner when it's no longer needed.
     */
    public void close() {
        scanner.close();
    }
}
