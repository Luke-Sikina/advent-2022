import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 4 P1");
        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(
                sum -> System.out.println("There are " + sum + " pairs that fully overlap"),
                () -> System.out.println("Bad input")
            );

    }

    private record Range(int start, int end) {}

    private record Pair(Range a, Range b) {}

    private int part1(List<Pair> pairs) {
        return (int) pairs.stream()
            .filter(this::fullyOverlap)
            .count();
    }
    private boolean fullyOverlap(Pair pair) {
        Range a = pair.a();
        Range b = pair.b();
        //1-1,1-89
        if (a.start() == b.start() || a.end() == b.end()) {
            return true;
        }
        if (a.start() < b.start()) {
            return a.end() >= b.end();
        } else {
            return b.end() >= a.end();
        }
    }
    private Pair parseLine(String line) {
        List<Range> ranges = Arrays.stream(line.split(","))
            .map(range -> {
                String[] startEnd = range.split("-");
                return new Range(Integer.parseInt(startEnd[0]), Integer.parseInt(startEnd[1]));
            })
            .toList();

        return new Pair(ranges.get(0), ranges.get(1));
    }

    private Optional<List<Pair>> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            List<Pair> pairs = readFromInputStream(inputStream).stream()
                .map(this::parseLine)
                .toList();
            return Optional.of(pairs);
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
