import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println("Day 8 part 1");
        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(
                i -> System.out.println("Visible tree count: " + i),
                () -> System.out.println("Bad input")
            );
    }
    
    private int part1(Grid grid) {
        Set<Point> visibleTrees = new HashSet<>();
        for (int x = 0; x < grid.x(); x++) {
            List<Point> column = grid.column(x);
            findVisible(visibleTrees, column);
            Collections.reverse(column);
            findVisible(visibleTrees, column);
        }
        for (int y = grid.y() - 1; y >= 0; y--) {
            List<Point> row = grid.row(y);
            findVisible(visibleTrees, row);
            Collections.reverse(row);
            findVisible(visibleTrees, row);
        }

        return visibleTrees.size();
    }

    private static void findVisible(Set<Point> visibleTrees, List<Point> points) {
        int maxHeight = -1;
        for (Point point : points) {
            if (point.height() > maxHeight) {
                maxHeight = point.height();
                visibleTrees.add(point);
            }
        }
    }

    private Optional<Grid> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            int[][] rawGrid = readFromInputStream(inputStream).stream()
                .map(c -> c.chars().boxed().mapToInt(ch -> Integer.parseInt("" + (char) ch.intValue())).toArray())
                .toArray(int[][]::new);
            Point[][] grid = new Point[rawGrid.length][];
            for (int x = 0; x < rawGrid.length; x++) {
                int[] row = rawGrid[x];
                grid[x] = new Point[row.length];
                for (int y = 0; y < row.length; y++) {
                    grid[x][y] = new Point(x, y, row[y]);
                }
            }
            
            return Optional.of(new Grid(grid));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private List<String> readFromInputStream(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
