package com.algobench.pro.algorithm.graph;

/**
 * {@code Edge} class represents a weighted edge in a graph, connecting a source vertex to a destination vertex.
 * <p>
 * Each edge has a destination vertex and a weight, which can represent distance, cost, or any other relevant metric
 * for graph algorithms.
 *
 * <p><b>Type Parameter:</b></p>
 * <ul>
 *     <li>{@code <T>} - The type of vertices that this edge connects.</li>
 * </ul>
 *
 * @param <T> the type of vertices connected by this edge
 * @version 1.0
 */
public class Edge<T> {
    private final T destination;
    private final double weight;

    /**
     * Constructs a new edge with a specified destination vertex and weight.
     *
     * @param destination The vertex that this edge points to. Must not be null.
     * @param weight      The weight of this edge, representing the cost or distance to traverse to the destination vertex.
     * @throws IllegalArgumentException if the destination vertex is null.
     */
    public Edge(T destination, double weight) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination vertex cannot be null");
        }
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Retrieves the destination vertex of this edge.
     *
     * @return The vertex at the end of this edge.
     */
    public T getDestination() {
        return destination;
    }

    /**
     * Retrieves the weight of this edge.
     *
     * @return The weight of this edge.
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        return destination.equals(edge.destination);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = destination.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
               "destination=" + destination +
               ", weight=" + weight +
               '}';
    }
}
