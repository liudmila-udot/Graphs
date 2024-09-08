import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/rotting-oranges
 */
public class RottingOranges {

    public static int[][] OFFSETS = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public static int FRESH = 1;
    public static int ROTTED = 2;

    public static int orangesRotting(int[][] grid) {
        // Do BSF from each rotting orange until all reachable oranges are rotted
        int minutes = -1;
        int fresh = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i <= grid.length - 1; i++) {
            for (int j = 0; j <= grid[0].length - 1; j++) {
                if (grid[i][j] == ROTTED) {
                    queue.add(new int[]{i, j});
                } else if (grid[i][j] == FRESH) {
                    fresh++;
                }
            }
        }
        if (fresh == 0) {
            return 0;
        }

        while (!queue.isEmpty()) {
            minutes++;
            int currentCycle = queue.size();
            while (currentCycle > 0) {
                currentCycle--;
                int[] current = queue.poll();
                for (int[] offset : OFFSETS) {
                    int row = current[0] + offset[0];
                    int col = current[1] + offset[1];
                    if (row >= 0 && row <= grid.length - 1 && col >= 0 && col <= grid[0].length - 1) {
                        if (grid[row][col] == FRESH) {
                            grid[row][col] = ROTTED;
                            queue.add(new int[]{row, col});
                            fresh--;
                        }
                    }
                }
            }
        }

        return fresh == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        int[][] grid = {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        System.out.println(orangesRotting(grid));
    }
}
