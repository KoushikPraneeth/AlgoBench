package com.algobench.pro.algorithm.graph;

/**
 * Represents an edge in a graph, connecting two vertices.
 * @param <T> The type of vertices the edge connects
 */
public class Edge<T> {
    private final T destination;
    private final double weight;

    /**
     * Creates a new edge with the specified destination and weight.
     * @param destination The vertex this edge points to
     * @param weight The weight of the edge
     */
    public Edge(T destination, double weight) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination vertex cannot be null");
        }
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Gets the destination vertex of this edge.
     * @return The destination vertex
     */
    public T getDestination() {
        return destination;
    }

    /**
     * Gets the weight of this edge.
     * @return The edge weight
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
