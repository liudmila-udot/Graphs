import java.util.*;

/**
 * Created by Liudmila on 19.11.2017.
 */
public class BFS {

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

    public static List<Integer> bfs(Graph graph, Integer root){
        Set<Integer> visited = new HashSet<>();
        List<Integer> ret = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();

        queue.add(root);
        ret.add(root);
        visited.add(root);

        while (!queue.isEmpty()){
            Integer node = queue.poll();

            List<Integer> unvisited = getUnvisitedChildNodes(graph, visited, node);

            for (Integer unvisitedChild: unvisited){
                queue.add(unvisitedChild);
                ret.add(unvisitedChild);
                visited.add(unvisitedChild);
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
        Graph graph = new Graph(8);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 7);
        graph.addEdge(3, 8);

        System.out.println(bfs(graph, 1));
    }
}
