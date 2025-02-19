package com.algobench.pro.algorithm.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implements Dijkstra's algorithm to find the shortest paths from a single source vertex to all other vertices
 * in a weighted graph.
 * <p>
 * Dijkstra's algorithm is used for finding the shortest paths between nodes in a graph, which may represent, for example,
 * road networks. It is particularly useful for finding the shortest paths in networks where edge weights are non-negative.
 *
 * <p><b>Type Parameter:</b></p>
 * <ul>
 *     <li>{@code <T>} - The type of vertices in the graph.</li>
 * </ul>
 *
 * @param <T> the type of vertices in the graph
 * @version 1.0
 */
public class DijkstraAlgorithm<T> {
    private static final Logger logger = LogManager.getLogger(DijkstraAlgorithm.class);
    private final Graph<T> graph;

    /**
     * Constructs a DijkstraAlgorithm instance for a given graph.
     *
     * @param graph The graph on which Dijkstra's algorithm will be applied.
     * @throws IllegalArgumentException if the graph is null.
     */
    public DijkstraAlgorithm(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
        logger.debug("DijkstraAlgorithm initialized");
    }

    /**
     * Executes Dijkstra's algorithm to compute the shortest paths from a source vertex to all reachable vertices
     * in the graph.
     *
     * @param source The starting vertex from which to compute distances.
     * @return A map containing the shortest distance from the source vertex to each vertex in the graph.
     *         Vertices not reachable from the source are not included in this map.
     * @throws IllegalArgumentException if the source vertex is not found in the graph.
     */
    public Map<T, Double> findShortestPaths(T source) {
        if (!graph.hasVertex(source)) {
            logger.error("Source vertex not in graph: {}", source);
            throw new IllegalArgumentException("Source vertex not in graph");
        }

        logger.info("Finding shortest paths from source: {}", source);
        Map<T, Double> distances = new HashMap<>();
        Map<T, T> previousVertices = new HashMap<>();
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();

        // Initialize distances
        for (T vertex : graph.getVertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(source, 0.0);
        priorityQueue.add(new VertexDistance<>(source, 0.0));

        while (!priorityQueue.isEmpty()) {
            T currentVertex = priorityQueue.poll().getVertex();

            for (Edge<T> edge : graph.getEdges(currentVertex)) {
                T neighbor = edge.getDestination();
                double weight = edge.getWeight();
                double distanceThroughCurrent = distances.get(currentVertex) + weight;

                if (distanceThroughCurrent < distances.get(neighbor)) {
                    distances.put(neighbor, distanceThroughCurrent);
                    previousVertices.put(neighbor, currentVertex);
                    priorityQueue.add(new VertexDistance<>(neighbor, distanceThroughCurrent));
                    logger.debug("Updated shortest distance to vertex: {}", neighbor);
                }
            }
        }

        logger.info("Shortest paths calculation completed from source: {}", source);
        return distances;
    }

    /**
     * Inner class to represent vertex-distance pairs for priority queue.
     */
    private static class VertexDistance<T> implements Comparable<VertexDistance<T>> {
        private final T vertex;
        private final double distance;

        public VertexDistance(T vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public T getVertex() {
            return vertex;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public int compareTo(VertexDistance<T> other) {
            return Double.compare(distance, other.distance);
        }
    }
}
