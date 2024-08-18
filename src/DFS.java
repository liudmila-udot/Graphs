import java.util.*;

/**
 * Created by Liudmila on 04.11.2017.
 */
public class DFS {

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
            adj.get(v).add(u);
        }
    }

    public static List<Integer> dfs(Map<Integer, List<Integer>> adjList, Integer root){
        Set<Integer> visited = new HashSet<>();
        List<Integer> ret = new ArrayList<>();

        LinkedList<Integer> stack = new LinkedList<>();
        stack.add(root);

        ret.add(root);
        visited.add(root);

        while (!stack.isEmpty()){
            Integer node = stack.peek();

            List<Integer> unvisited = getUnvisitedChildNodes(adjList, visited, node);

            if (!unvisited.isEmpty()){
                Integer child = unvisited.get(0);
                if(child != null) {
                    ret.add(child);
                    visited.add(child);
                    stack.push(child);
                }
            } else {
                stack.pop();
            }
        }

        return ret;
    }

    public static List<Integer> getUnvisitedChildNodes(Map<Integer, List<Integer>> adjList, Set<Integer> visited, Integer parent){
        Set<Integer> copy = new HashSet<>(adjList.get(parent));
        copy.removeAll(visited);
        return new ArrayList<>(copy);
    }

    // Function to perform DFS traversal starting from a given vertex
    public static List<Integer> dfsRecursive(Map<Integer, List<Integer>> adjList, Integer startVertex) {
        Set<Integer> visited = new HashSet<>(); // Set to keep track of visited vertices
        List<Integer> ret = new ArrayList<>();
        dfsRecursiveUtil(adjList, startVertex, visited, ret);
        return ret;
    }

    // Recursive utility function for DFS traversal
    private static void dfsRecursiveUtil(Map<Integer, List<Integer>> adjList, Integer vertex, Set<Integer> visited, List<Integer> ret) {
        visited.add(vertex);
        ret.add(vertex);

        // Recursively visit all adjacent vertices of the current vertex
        for (int neighbor : adjList.getOrDefault(vertex, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                dfsRecursiveUtil(adjList, neighbor, visited, ret);
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 7);
        graph.addEdge(3, 8);

        System.out.println(dfs(graph.adj, 1));
    }
}
