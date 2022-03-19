import java.util.*;

/**
 * https://www.careercup.com/question?id=5631096870928384
 */
public class CurrencyExchangeRate {

	static class Graph {

		private final Map<String, Map<String, Double>> graph;

		public Graph() {
			this.graph = new HashMap<>();
		}

		public void put(String dividend, String divisor, double quotient) {
			addEdge(dividend, divisor, quotient);
			addEdge(divisor, dividend, 1 / quotient);
		}

		private void addEdge(String key1, String key2, double value) {
			if (graph.containsKey(key1)) {
				graph.get(key1).put(key2, value);
			} else {
				Map<String, Double> map = new HashMap<>();
				map.put(key2, value);
				graph.put(key1, map);
			}
		}

		/**
		 * BFS has an interesting property.
		 * It can be used to find the shortest path in Undirected Unweighted graph.
		 *
		 * @param rootNode start node
		 * @param endNode  end node
		 * @return shortest path
		 */
		public List<String> bfsShortestPath(String rootNode, String endNode) {
			Set<String> visited = new HashSet<>();
			Stack<String> stack = new Stack<>();

			Queue<String> queue = new LinkedList<>();

			queue.add(rootNode);
			stack.push(rootNode);
			visited.add(rootNode);

			// 1. Do BFS and store visited nodes in stack
			while (!queue.isEmpty()) {
				String current = queue.poll();

				for (Map.Entry<String, Double> currencyCoefficient : graph.get(current).entrySet()) {
					String neighbor = currencyCoefficient.getKey();
					if (!visited.contains(neighbor)) {
						queue.add(neighbor);
						stack.push(neighbor);
						visited.add(neighbor);
						if (Objects.equals(neighbor, endNode)) {
							queue.clear();
							break;
						}
					}
				}
			}

			// 2. backtrack the shortest path
			// we should find a previous node on each level up to start node
			List<String> ret = new ArrayList<>();
			ret.add(stack.peek());

			String node;
			String currentNode = stack.peek();

			while (!stack.isEmpty()) {
				node = stack.pop();
				if (graph.get(currentNode).containsKey(node)) {
					ret.add(node);
					currentNode = node;
				}
			}

			//Reverse the route - bring start to the front
			Collections.reverse(ret);
			return ret;
		}

		public double getRate(String start, String end) {
			if (start.equals(end)){
				return 1;
			}
			// 1. find the shortest path
			List<String> path = bfsShortestPath(start, end);
			// 2. calculate conversion rate
			String current = path.get(0);
			double rate = 1d;
			for (int i = 1; i <= path.size() - 1; i++) {
				String next = path.get(i);
				rate = rate * graph.get(current).get(next);
				current = next;
			}
			return rate;
		}
	}


	/**
	 * Given a list of currency exchange rates like this:
	 * EUR/USD => 1.2
	 * USD/GBP => 0.75
	 * GBP/AUD => 1.7
	 * AUD/JPY => 90
	 * GBP/JPY => 150
	 * JPY/INR => 0.6
	 * <p>
	 * write a method convert
	 * <p>
	 * For example, convert(EUR, 100, INR)
	 * <p>
	 * The method should minimize the number of intermediate conversions: find the shortest path.
	 */
	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.put("EUR", "USD", 1.2d);
		graph.put("USD", "GBP", 0.75d);
		graph.put("GPB", "AUD", 1.7d);
		graph.put("AUD", "GPY", 90d);
		graph.put("GBP", "GPY", 150d);
		graph.put("GPY", "INR", 0.6d);

		System.out.println(graph.bfsShortestPath("EUR", "GBP"));
		System.out.println(graph.getRate("EUR", "GBP"));

		// [["x1","x2"],["x2","x3"],["x1","x4"],["x2","x5"]]
		// [3.0,0.5,3.4,5.6]
		// [["x2","x4"],["x1","x5"],["x1","x3"],["x5","x5"],["x5","x1"],["x3","x4"],["x4","x3"],["x6","x6"],["x0","x0"]]
		// [1.1333333333333333, 16.8, 1.5, 1.0, 0.05952380952380952]
		Graph graph2 = new Graph();
		graph2.put("x1", "x2", 3.0d);
		graph2.put("x2", "x3", 0.5d);
		graph2.put("x1", "x4", 3.4d);
		graph2.put("x2", "x5", 5.6d);

		System.out.println(graph2.getRate("x2", "x4"));
		System.out.println(graph2.getRate("x1", "x5"));
		System.out.println(graph2.getRate("x1", "x3"));
		System.out.println(graph2.getRate("x5", "x5"));
		System.out.println(graph2.getRate("x5", "x1"));
	}
}
