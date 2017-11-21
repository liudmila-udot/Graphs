import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Liudmila Udot
 * @since 2017.3
 */
public class SnakeAndLadder {

    static class Graph {

        int vertexCount;
        Map<Integer, List<Integer>> adj; // Adjacency list

        Graph(int vertexCount) {
            this.vertexCount = vertexCount;
            adj = new HashMap<Integer, List<Integer>>();
        }

        void addEdge(int u, int v) {
            if (!adj.containsKey(u)) {
                adj.put(u, new ArrayList<Integer>());
            }
            if (!adj.containsKey(v)) {
                adj.put(v, new ArrayList<Integer>());
            }
            adj.get(u).add(v);
        }

        void replaceEdge(int oldV, int newV) {
            adj.remove(oldV);

            for (Map.Entry<Integer, List<Integer>> entry : adj.entrySet()) {
                entry.getValue().replaceAll(e -> {
                    if (e == oldV) {
                        return newV;
                    }
                    return e;
                });
            }
        }
    }

    /**
     * Test method, not used in main
     *
     * @param graph
     * @param root
     * @param endNode
     * @return
     */
    public static List<List<Integer>> levels(Graph graph, Integer root, Integer endNode) {
        Set<Integer> visited = new HashSet<>();
        int level = 0;

        Queue<Integer> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);
        List<List<Integer>> levels = new ArrayList<>();

        while (!queue.isEmpty()) {
            List<Integer> resultOnLevel = new ArrayList<>();
            int countOnLevel = queue.size();
            while (countOnLevel > 0) {
                Integer node = queue.poll();
                resultOnLevel.add(node);
                if (Objects.equals(node, endNode)) {
                    queue.clear();
                    break;
                }

                countOnLevel--;
                List<Integer> unvisited = getUnvisitedChildNodes(graph, visited, node);

                for (Integer unvisitedChild : unvisited) {
                    queue.add(unvisitedChild);
                    visited.add(unvisitedChild);
                }

            }
            levels.add(resultOnLevel);
        }

        return levels;
    }

    public static int level(Graph graph, Integer root, Integer endNode) {
        Set<Integer> visited = new HashSet<>();
        int level = 0;

        Queue<Integer> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {

            int countOnLevel = queue.size();
            while (countOnLevel > 0) {
                Integer node = queue.poll();
                if (Objects.equals(node, endNode)) {
                    queue.clear();
                    break;
                }

                countOnLevel--;
                List<Integer> unvisited = getUnvisitedChildNodes(graph, visited, node);

                for (Integer unvisitedChild : unvisited) {
                    queue.add(unvisitedChild);
                    visited.add(unvisitedChild);
                }

            }
            level++;
        }

        return level;
    }

    public static List<Integer> getUnvisitedChildNodes(Graph graph, Set<Integer> visited, Integer parent) {
        if (graph.adj.get(parent) != null) {
            Set<Integer> copy = new HashSet<>(graph.adj.get(parent));
            copy.removeAll(visited);
            return new ArrayList<Integer>(copy);
        }
        return new ArrayList<Integer>();
    }

    public static Graph snakeAndLadderBoard(int vertical, int horizontal) {
        int vertex = vertical * horizontal;
        Graph graph = new Graph(vertical * horizontal);

        for (int i = 1; i <= vertex; i++) {
            for (int j = 1; j <= 6; j++) {
                if (i + j <= vertex) {
                    graph.addEdge(i, i + j);
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();
        for (int i = 1; i <= testCases; i++) {
            Graph graph = snakeAndLadderBoard(5, 6);
            int snakeLaddersCount = sc.nextInt();
            int root = 1;
            for (int p_i = 0; p_i < snakeLaddersCount; p_i++) {
                int from = sc.nextInt();
                int to = sc.nextInt();
                graph.replaceEdge(from, to);
                if (from == 1){
                    root = to;
                }
            }
            System.out.println(level(graph, root, 30) - 1);
        }
    }
}
