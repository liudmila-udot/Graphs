import java.util.*;

/**
 * https://leetcode.com/problems/course-schedule-ii/
 */
public class CourseScheduleII {

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> adjList = toAdjList(prerequisites);
        int[] indegree = getIndegree(numCourses, adjList);
        return topoSort(adjList, indegree);
    }

    private static int[] topoSort(Map<Integer, List<Integer>> adjList, int[] indegree) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i <= indegree.length - 1; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> ret = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            ret.add(current);

            for (Integer prerequisiteFor : adjList.getOrDefault(current, new ArrayList<>())) {
                indegree[prerequisiteFor]--;

                if (indegree[prerequisiteFor] == 0) {
                    queue.add(prerequisiteFor);
                }
            }
        }

        if (ret.size() == indegree.length) {
            return ret
                    .stream()
                    .mapToInt(Integer::valueOf)
                    .toArray();
        }

        return new int[0];
    }

    private static int[] getIndegree(int numCourses, Map<Integer, List<Integer>> adjList) {
        int[] indegree = new int[numCourses];
        for (Integer prerequisite : adjList.keySet()) {
            for (Integer prerequisiteFor : adjList.get(prerequisite)) {
                indegree[prerequisiteFor]++;
            }
        }
        return indegree;
    }

    private static Map<Integer, List<Integer>> toAdjList(int[][] prerequisites) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i <= prerequisites.length - 1; i++) {
            int prerequisiteFor = prerequisites[i][0];
            int prerequisite = prerequisites[i][1];

            List<Integer> prerequisiteForList = adjList.getOrDefault(prerequisite, new ArrayList<>());
            prerequisiteForList.add(prerequisiteFor);

            adjList.put(prerequisite, prerequisiteForList);
        }
        return adjList;
    }

    public static void main(String[] args) {
        int numCourses = 4;
        int[][] prerequisites = new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        System.out.println(Arrays.toString(findOrder(numCourses, prerequisites)));
    }
}
