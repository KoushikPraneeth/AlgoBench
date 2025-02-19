package com.algobench.pro.visualization.chart;

/**
 * Represents a point in 2D space with x and y coordinates.
 */
public class Point2D {
    private final double x;
    private final double y;

    /**
     * Creates a new point with the given coordinates.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate.
     * @return The x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     * @return The y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the distance to another point.
     * @param other The other point
     * @return The Euclidean distance between this point and the other point
     */
    public double distanceTo(Point2D other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Double.compare(point2D.x, x) == 0 &&
               Double.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        return result;
    }
}
