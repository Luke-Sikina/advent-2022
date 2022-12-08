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

        System.out.println("Day 8 part w");
        s.parseInput()
            .map(s::part2)
            .ifPresentOrElse(
                i -> System.out.println("Max vis score: " + i),
                () -> System.out.println("Bad input")
            );
    }
    
    private int part1(Grid grid) {
        Set<Point> visibleTrees = new HashSet<>();
        for (int x = 0; x < grid.x(); x++) {
            List<PointWithVisibility> column = grid.column(x);
            findVisible(visibleTrees, column);
            Collections.reverse(column);
            findVisible(visibleTrees, column);
        }
        for (int y = grid.y() - 1; y >= 0; y--) {
            List<PointWithVisibility> row = grid.row(y);
            findVisible(visibleTrees, row);
            Collections.reverse(row);
            findVisible(visibleTrees, row);
        }

        return visibleTrees.size();
    }

    private int part2(Grid grid) {
        for (int x = 0; x < grid.x(); x++) {
            PointWithVisibility prev = new PointWithVisibility(new Point(-1, -1, Integer.MAX_VALUE));
            List<PointWithVisibility> column = grid.column(x);
            for (PointWithVisibility current : column) {
                current.updateYNeg(prev);
                prev = current;
            }

            Collections.reverse(column);
            prev = new PointWithVisibility(new Point(-1, -1, Integer.MAX_VALUE));
            for (PointWithVisibility current : column) {
                current.updateYPos(prev);
                prev = current;
            }
        }

        for (int y = grid.y() - 1; y >= 0; y--) {
            PointWithVisibility prev = new PointWithVisibility(new Point(-1, -1, Integer.MAX_VALUE));
            List<PointWithVisibility> row = grid.row(y);
            for (PointWithVisibility current : row) {
                current.updateXNeg(prev);
                prev = current;
            }

            Collections.reverse(row);
            prev = new PointWithVisibility(new Point(-1, -1, Integer.MAX_VALUE));
            for (PointWithVisibility current : row) {
                current.updateXPos(prev);
                prev = current;
            }
        }

        return Arrays.stream(grid.points())
            .flatMap(Arrays::stream)
            .mapToInt(PointWithVisibility::calcScore)
            .max()
            .orElse(0);
    }

    private static void findVisible(Set<Point> visibleTrees, List<PointWithVisibility> points) {
        int maxHeight = -1;
        for (PointWithVisibility point : points) {
            if (point.point().height() > maxHeight) {
                maxHeight = point.point().height();
                visibleTrees.add(point.point());
            }
        }
    }

    private Optional<Grid> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            int[][] rawGrid = readFromInputStream(inputStream).stream()
                .map(c -> c.chars().boxed().mapToInt(ch -> Integer.parseInt("" + (char) ch.intValue())).toArray())
                .toArray(int[][]::new);
            PointWithVisibility[][] grid = new PointWithVisibility[rawGrid.length][];
            for (int x = 0; x < rawGrid.length; x++) {
                int[] row = rawGrid[x];
                grid[x] = new PointWithVisibility[row.length];
                for (int y = 0; y < row.length; y++) {
                    grid[x][y] = new PointWithVisibility(new Point(x, y, row[y]));
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
