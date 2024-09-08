import java.util.*;

/**
 * https://leetcode.com/problems/number-of-islands
 */
public class NumberOfIslands {

    public static int[][] OFFSETS = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public static int numIslandsNoRewrite(char[][] grid) {
        int numberOfIslands = 0;
        Set<List<Integer>> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        // Do BSF for each cell (if not yet visited)
        // and increase the result number
        for (int i = 0; i <= grid.length - 1; i++) {
            for (int j = 0; j <= grid[0].length - 1; j++) {
                if (grid[i][j] == '1' && !visited.contains(getCell(i, j))) {
                    numberOfIslands++;
                    queue.add(new int[]{i, j});
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        visited.add(getCell(current[0], current[1]));
                        for (int[] offset : OFFSETS) {
                            if ((current[0] + offset[0]) >= 0 && (current[0] + offset[0]) <= grid.length - 1 &&
                                    (current[1] + offset[1]) >= 0 && (current[1] + offset[1]) <= grid[0].length - 1
                            ) {
                                if (!visited.contains(getCell(current[0] + offset[0], current[1] + offset[1])) && grid[current[0] + offset[0]][current[1] + offset[1]] == '1') {
                                    queue.add(new int[]{current[0] + offset[0], current[1] + offset[1]});
                                }
                            }
                        }
                    }
                }
            }
        }

        return numberOfIslands;
    }

    private static List<Integer> getCell(int i, int j) {
        List<Integer> currentCell = new ArrayList<>();
        currentCell.add(i);
        currentCell.add(j);
        return currentCell;
    }

    public static int numIslandsNoRewriteVisitedArray(char[][] grid) {
        int numberOfIslands = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> queue = new LinkedList<>();
        // Do BSF for each cell (if not yet visited)
        // and increase the result number
        for (int i = 0; i <= grid.length - 1; i++) {
            for (int j = 0; j <= grid[0].length - 1; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    numberOfIslands++;
                    queue.add(new int[]{i, j});
                    visited[i][j] = true;
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        for (int[] offset : OFFSETS) {
                            if ((current[0] + offset[0]) >= 0 && (current[0] + offset[0]) <= grid.length - 1 &&
                                    (current[1] + offset[1]) >= 0 && (current[1] + offset[1]) <= grid[0].length - 1
                            ) {
                                if (!visited[current[0] + offset[0]][current[1] + offset[1]] && grid[current[0] + offset[0]][current[1] + offset[1]] == '1') {
                                    queue.add(new int[]{current[0] + offset[0], current[1] + offset[1]});
                                    visited[current[0] + offset[0]][current[1] + offset[1]] = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return numberOfIslands;
    }

    public static int numIslandsAlterArray(char[][] grid) {
        int numberOfIslands = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<int[]> queue = new LinkedList<>();
        // Do BSF for each cell (if not yet visited)
        // and increase the result number
        for (int i = 0; i <= grid.length - 1; i++) {
            for (int j = 0; j <= grid[0].length - 1; j++) {
                if (grid[i][j] == '1') {
                    numberOfIslands++;
                    queue.add(new int[]{i, j});
                    grid[i][j] = '0';
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        for (int[] offset : OFFSETS) {
                            if ((current[0] + offset[0]) >= 0 && (current[0] + offset[0]) <= grid.length - 1 &&
                                    (current[1] + offset[1]) >= 0 && (current[1] + offset[1]) <= grid[0].length - 1
                            ) {
                                if (grid[current[0] + offset[0]][current[1] + offset[1]] == '1') {
                                    queue.add(new int[]{current[0] + offset[0], current[1] + offset[1]});
                                    grid[current[0] + offset[0]][current[1] + offset[1]] = '0';
                                }
                            }
                        }
                    }
                }
            }
        }

        return numberOfIslands;
    }


    public static void main(String[] args) {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        System.out.println(numIslandsNoRewriteVisitedArray(grid));
    }
}
