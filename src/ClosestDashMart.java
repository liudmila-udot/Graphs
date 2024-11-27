import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/discuss/interview-question/1522955/Doordash-Onsite
 * A DashMart is a warehouse run by DoorDash that houses items found in
 * convenience stores, grocery stores, and restaurants. We have a city with open
 * roads, blocked-off roads, and DashMarts.
 * City planners want you to identify how far a location is from its closest
 * DashMart.
 * You can only travel over open roads (up, down, left, right).
 * Locations are given in [row, col] format.
 * Example:
 * [
 * # 0 1 2 3 4 5 6 7 8
 * ['X', ' ', ' ', 'D', ' ', ' ', 'X', ' ', 'X'], # 0
 * ['X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X'], # 1
 * [' ', ' ', ' ', 'D', 'X', 'X', ' ', 'X', ' '], # 2
 * [' ', ' ', ' ', 'D', ' ', 'X', ' ', ' ', ' '], # 3
 * [' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X'], # 4
 * [' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X', 'X'] # 5
 * ]
 * ' ' represents an open road that you can travel over in any direction (up, down, left, or right).
 * 'X' represents an blocked road that you cannot travel through.
 * 'D' represents a DashMart.
 * # list of pairs [row, col]
 * locations = [
 * [2, 2],
 * [4, 0],
 * [0, 4],
 * [2, 6],
 * ]
 * answer = [1, 4, 1, 5]
 * Implement Function:
 * - get_closest_dashmart(city, locations)
 * Provided:
 * - city: List[str]
 * - locations: List[List[int]]
 * Return:
 * - answer: List[int]
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

        // for each next cell in BSF set distance as current + 1
        // NOTE: we start with 'D' so we'll have to do the math to calculate actual distance as number
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
        char[][] grid = {{'X', ' ', ' ', 'D', ' ', ' ', 'X', ' ', 'X'}, {'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', 'X'}, {' ', ' ', ' ', 'D', 'X', 'X', ' ', 'X', ' '}, {' ', ' ', ' ', 'D', ' ', 'X', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X'}, {' ', ' ', ' ', ' ', 'X', ' ', ' ', 'X', 'X'}};

        int[][] locations = {{2, 2}, {4, 0}, {0, 4}, {2, 6},};

        System.out.println(Arrays.toString(distances(grid, locations)));
    }
}
