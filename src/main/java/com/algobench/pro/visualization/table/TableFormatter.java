package com.algobench.pro.visualization.table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Formats data into ASCII tables for console output.
 */
public class TableFormatter {
    private static final Logger logger = LogManager.getLogger(TableFormatter.class);
    private static final char HORIZONTAL = '─';
    private static final char VERTICAL = '│';
    private static final char TOP_LEFT = '┌';
    private static final char TOP_RIGHT = '┐';
    private static final char BOTTOM_LEFT = '└';
    private static final char BOTTOM_RIGHT = '┘';
    private static final char T_DOWN = '┬';
    private static final char T_UP = '┴';
    private static final char T_RIGHT = '├';
    private static final char T_LEFT = '┤';
    private static final char CROSS = '┼';

    private final List<String[]> rows;
    private final String[] headers;
    private final int[] columnWidths;
    private final int numColumns;

    /**
     * Creates a new table with the specified headers.
     * @param headers Column headers
     */
    public TableFormatter(String... headers) {
        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Headers cannot be null or empty");
        }
        this.headers = headers;
        this.numColumns = headers.length;
        this.rows = new ArrayList<>();
        this.columnWidths = new int[numColumns];

        // Initialize column widths with header lengths
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = headers[i].length();
        }

        logger.debug("Created table with {} columns", numColumns);
    }

    /**
     * Adds a row to the table.
     * @param values Row values (must match number of columns)
     */
    public void addRow(String... values) {
        if (values == null || values.length != numColumns) {
            throw new IllegalArgumentException(
                "Number of values must match number of columns");
        }

        // Update column widths if necessary
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = Math.max(columnWidths[i], values[i].length());
        }

        rows.add(values);
        logger.debug("Added row to table: {}", Arrays.toString(values));
    }

    /**
     * Formats the table as a string.
     * @return Formatted table string
     */
    public String format() {
        StringBuilder table = new StringBuilder();

        // Add top border
        appendHorizontalBorder(table, TOP_LEFT, T_DOWN, TOP_RIGHT);

        // Add headers
        appendRow(table, headers);
        
        // Add header-content separator
        appendHorizontalBorder(table, T_RIGHT, CROSS, T_LEFT);

        // Add content rows
        for (int i = 0; i < rows.size(); i++) {
            appendRow(table, rows.get(i));
            if (i < rows.size() - 1) {
                appendHorizontalBorder(table, T_RIGHT, CROSS, T_LEFT);
            }
        }

        // Add bottom border
        appendHorizontalBorder(table, BOTTOM_LEFT, T_UP, BOTTOM_RIGHT);

        logger.debug("Formatted table with {} rows", rows.size());
        return table.toString();
    }

    private void appendRow(StringBuilder sb, String[] values) {
        sb.append(VERTICAL);
        for (int i = 0; i < numColumns; i++) {
            sb.append(String.format(" %-" + columnWidths[i] + "s ", values[i]));
            sb.append(VERTICAL);
        }
        sb.append('\n');
    }

    private void appendHorizontalBorder(StringBuilder sb, char left, char middle, char right) {
        sb.append(left);
        for (int i = 0; i < numColumns; i++) {
            sb.append(String.valueOf(HORIZONTAL).repeat(columnWidths[i] + 2));
            sb.append(i < numColumns - 1 ? middle : right);
        }
        sb.append('\n');
    }

    /**
     * Creates a table representation of a 2D array.
     * @param data 2D array of data
     * @param headers Column headers
     * @return Formatted table string
     */
    public static String formatArray(Object[][] data, String... headers) {
        if (data == null || data.length == 0 || headers == null || headers.length == 0) {
            return "No data to display";
        }

        TableFormatter formatter = new TableFormatter(headers);
        for (Object[] row : data) {
            String[] stringRow = Arrays.stream(row)
                                    .map(String::valueOf)
                                    .toArray(String[]::new);
            formatter.addRow(stringRow);
        }

        return formatter.format();
    }
}
