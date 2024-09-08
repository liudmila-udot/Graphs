import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/discuss/interview-question/1522955/Doordash-Onsite
 */
public class ClosestDashMart {

    public static char DASHMART = 'D';
    public static char EMPTY = ' ';
    // unused
    //public static char OBSTACLE = 'X';
    public static int[][] OFFSETS = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public static int[] distances(char[][] grid, int[][] locations) {
        // Do BSF from each DASHMART until no unreachable EMPTY cells left
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i <= grid.length - 1; i++) {
            for (int j = 0; j <= grid[0].length - 1; j++) {
                if (grid[i][j] == DASHMART) {
                    queue.add(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int[] offset : OFFSETS) {
                int row = current[0] + offset[0];
                int col = current[1] + offset[1];
                if (row >= 0 && row <= grid.length - 1 && col >= 0 && col <= grid[0].length - 1) {
                    if (grid[row][col] == EMPTY) {
                        grid[row][col] = (char) (grid[current[0]][current[1]] + 1);
                        queue.add(new int[]{row, col});
                    }
                }
            }
        }

        int[] ret = new int[locations.length];
        for (int i = 0; i <= locations.length - 1; i++) {
            if (grid[locations[i][0]][locations[i][1]] == EMPTY) {
                ret[i] = -1;
            } else {
                // we started char with D by adding +1 each FSF iteration
                // distance between D and char in grid - will be int distance
                ret[i] = grid[locations[i][0]][locations[i][1]] - DASHMART;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'X', ' ', ' ', 'D', ' ', ' ', 'X', ' ', 'X'},
                {'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', 'D', 'X', 'X', ' ', 'X', ' '},
                {' ', ' ', ' ', 'D', ' ', 'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X', 'X'}};

        int[][] locations = {
                {2, 2},
                {4, 0},
                {0, 4},
                {2, 6},
        };

        System.out.println(Arrays.toString(distances(grid, locations)));
    }
}
