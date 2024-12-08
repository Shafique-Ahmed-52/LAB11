/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.lab_11;

import java.util.Scanner;
import java.util.*;

public class Graph {
    private int[][] adjMatrix;
    private int numVertices;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
    }

    // Task 1: Create and represent a graph using an adjacency matrix
    public void addEdge(int u, int v) {
        adjMatrix[u - 1][v - 1] = 1;
    }

    // Task 2: Display the adjacency matrix in a readable format
    public void displayAdjMatrix() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Task 3: Find the shortest path between two vertices using BFS
    public void findShortestPath(int start, int end) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numVertices];
        int[] prev = new int[numVertices];
        Arrays.fill(prev, -1);

        queue.add(start - 1);
        visited[start - 1] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();

            if (vertex == end - 1) {
                List<Integer> path = new ArrayList<>();
                for (int v = end - 1; v != -1; v = prev[v]) {
                    path.add(v + 1);
                }
                Collections.reverse(path);
                System.out.println("Shortest Path: " + path);
                System.out.println("Length: " + (path.size() - 1));
                return;
            }

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[vertex][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    prev[i] = vertex;
                }
            }
        }
        System.out.println("No path found");
    }

    // Task 4: Find all paths between two vertices using DFS
    public void findAllPaths(int start, int end) {
        List<List<Integer>> allPaths = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();
        dfsFindPaths(start - 1, end - 1, visited, path, allPaths);

        for (List<Integer> p : allPaths) {
            System.out.print("Path: ");
            for (int v : p) {
                System.out.print(v + " ");
            }
            System.out.println("(Length: " + (p.size() - 1) + ")");
        }
    }

    private void dfsFindPaths(int current, int end, boolean[] visited, List<Integer> path, List<List<Integer>> allPaths) {
        visited[current] = true;
        path.add(current + 1);

        if (current == end) {
            allPaths.add(new ArrayList<>(path));
        } else {
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[current][i] == 1 && !visited[i]) {
                    dfsFindPaths(i, end, visited, path, allPaths);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[current] = false;
    }

    // Task 5: Detect connected components in an undirected graph
    public void findConnectedComponents() {
        boolean[] visited = new boolean[numVertices];
        List<Set<Integer>> components = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                dfsFindComponent(i, visited, component);
                components.add(component);
            }
        }

        int componentNumber = 1;
        for (Set<Integer> component : components) {
            System.out.print("Component " + componentNumber++ + ": ");
            System.out.println(component);
        }
    }

    private void dfsFindComponent(int vertex, boolean[] visited, Set<Integer> component) {
        visited[vertex] = true;
        component.add(vertex + 1);

        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[vertex][i] == 1 && !visited[i]) {
                dfsFindComponent(i, visited, component);
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(4);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        System.out.println("Adjacency Matrix:");
        graph.displayAdjMatrix();

        System.out.println("\nTask 3: Shortest Path from 1 to 4");
        graph.findShortestPath(1, 4);

        System.out.println("\nTask 4: All Paths from 1 to 4");
        graph.findAllPaths(1, 4);

        System.out.println("\nTask 5: Connected Components");
        graph.findConnectedComponents();
    }
}
