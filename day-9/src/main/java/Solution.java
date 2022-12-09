import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 9 Part 1");
        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(i -> System.out.println("Squares: " + i), () -> System.out.println("Bad input"));

    }

    private static final Pattern moveRegex = Pattern.compile("^([LRUD]) (\\d+)$");

    private int part1(List<Move> moves) {
        ExpandingGrid grid = new ExpandingGrid(new Coordinate(0, 0), new Coordinate(0, 0), new HashSet<>());
        for (Move move : moves) {
            grid = grid.move(move.direction(), move.steps());
        }

        return grid.tailLocations().size();
    }

    private Optional<List<Move>> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            List<Move> moves = readFromInputStream(inputStream).stream()
                .map(moveRegex::matcher)
                .filter(Matcher::matches)
                .map(raw -> Direction.parse(raw.group(1)).map(d -> new Move(d, Integer.parseInt(raw.group(2)))))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

            return Optional.of(moves);
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
