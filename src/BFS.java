import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

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

    public static List<Integer> bfsShortestPath(Graph graph, Integer rootNode, Integer endNode){
        Set<Integer> visited = new HashSet<>();
        LinkedList<Integer> stack = new LinkedList<>();

        Queue<Integer> queue = new LinkedList<>();

        queue.add(rootNode);
        stack.push(rootNode);
        visited.add(rootNode);

        while (!queue.isEmpty()) {
            Integer node = queue.poll();

            List<Integer> unvisited = getUnvisitedChildNodes(graph, visited, node);

            for (Integer unvisitedChild : unvisited) {
                queue.add(unvisitedChild);
                stack.push(unvisitedChild);
                visited.add(unvisitedChild);
                if (Objects.equals(unvisitedChild, endNode)) {
                    queue.clear();
                    break;
                }
            }
        }

        List<Integer> ret = new ArrayList<>();
        ret.add(endNode);

        Integer node;
        Integer currentNode = endNode;

        while (!stack.isEmpty()) {
            node = stack.pop();
            if (graph.adj.get(currentNode).contains(node)){
                ret.add(node);
                currentNode = node;
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
        System.out.println(bfsShortestPath(graph, 1, 7));
    }
}
