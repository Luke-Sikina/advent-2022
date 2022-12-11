import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();

        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(
                i -> System.out.println("Number: " + i),
                () -> System.out.println("Fuck")
            );
    }

    public int part1(List<? extends Operation> operations) {
        int clock = 0;
        int register = 1;
        var checkpoints = new HashMap<Integer, Integer>();

        for (Operation operation : operations) {
            int cycles = 0;
            while (cycles < operation.clockCycles()) {
                cycles++;
                clock++;
            }
            if (clock % 20 == 0) {
                checkpoints.put(clock, register);
            }
            register = operation.updateState(register);
        }

        return checkpoints.entrySet().stream()
            .filter(e -> List.of(20, 60, 100, 120, 140, 180).contains(e.getKey()))
            .mapToInt(e -> e.getKey() * e.getValue())
            .sum();
    }

    private record Pair<L, R>(L l, R r) {};

    private static List<Pair<Pattern, Function<Matcher, ? extends Operation>>> matchers =
        List.of(
            new Pair<>(Pattern.compile("^noop$"), ignored -> new Operation.NoOp()),
            new Pair<>(Pattern.compile("^addx (-?\\d+)$"), (m) -> new Operation.Add(Integer.parseInt(m.group(1))))
        );

    private Optional<List<? extends Operation>> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            List<? extends Operation> operations = readFromInputStream(inputStream).stream()
                .map(this::match)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

            return Optional.of(operations);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<? extends Operation> match(String in) {
        return matchers.stream()
            .map(pair -> new Pair<>(pair.l().matcher(in), pair.r()))
            .filter(pair -> pair.l().find())
            .findAny()
            .map(pair -> pair.r().apply(pair.l()));
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
