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

    public static List<Integer> dsf(Graph graph, Integer root){
        Set<Integer> visited = new HashSet<>();
        List<Integer> ret = new ArrayList<>();

        LinkedList<Integer> stack = new LinkedList<>();
        stack.add(root);

        ret.add(root);
        visited.add(root);

        while (!stack.isEmpty()){
            Integer node = stack.peek();

            List<Integer> unvisited = getUnvisitedChildNodes(graph, visited, node);

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

    public static List<Integer> getUnvisitedChildNodes(Graph graph, Set<Integer> visited, Integer parent){
        Set<Integer> copy = new HashSet<>(graph.adj.get(parent));
        copy.removeAll(visited);
        return new ArrayList<Integer>(copy);
    }

    public static void main(String[] args) {
        Graph graph = new Graph(3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        System.out.println(dsf(graph, 1));
    }
}
