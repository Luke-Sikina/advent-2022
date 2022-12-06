import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution {
    private final DockParser dockParser;
    private final MovesParser movesParser;

    public Solution(DockParser dockParser, MovesParser movesParser) {
        this.dockParser = dockParser;
        this.movesParser = movesParser;
    }

    public static void main(String[] args) {
        Solution s = new Solution(new DockParser(), new MovesParser());
        System.out.println("Part 1");
        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(
                t -> System.out.println("The tops are: " + t),
                () -> System.out.println("Bad input")
            );
        System.out.println("Part 2");
        s.parseInput()
            .map(s::part2)
            .ifPresentOrElse(
                t -> System.out.println("The tops are: " + t),
                () -> System.out.println("Bad input")
            );
    }

    private record Input(Dock dock, List<Move> moves) {}

    private String part1(Input in) {
        in.moves().forEach(in.dock()::execute);
        return in.dock().getTopOfEachStack().stream()
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    private String part2(Input in) {
        in.moves().forEach(in.dock()::executeAllAtOnce);
        return in.dock().getTopOfEachStack().stream()
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    private Optional<Input> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            List<String> raw = readFromInputStream(inputStream);
            Dock dock = dockParser.parse(raw.subList(0, raw.indexOf("")));
            List<Move> moves = movesParser.parse(raw.subList(raw.indexOf("") + 1, raw.size()));

            return Optional.of(new Input(dock, moves));
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