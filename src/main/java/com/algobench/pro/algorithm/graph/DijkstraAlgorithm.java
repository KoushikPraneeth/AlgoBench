package com.algobench.pro.algorithm.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementation of Dijkstra's algorithm for finding shortest paths in a graph.
 * @param <T> The type of vertices in the graph
 */
public class DijkstraAlgorithm<T> {
    private static final Logger logger = LogManager.getLogger(DijkstraAlgorithm.class);
    private final Graph<T> graph;

    public DijkstraAlgorithm(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        this.graph = graph;
        logger.debug("DijkstraAlgorithm initialized");
    }

    /**
     * Finds the shortest paths from a source vertex to all other vertices in the graph.
     * @param source The source vertex
     * @return A map of vertices to their shortest distances from the source
     * @throws IllegalArgumentException if the source vertex is not in the graph
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
