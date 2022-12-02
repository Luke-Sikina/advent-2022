import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 2 P1 start: ");
        s.parseInput(s::parseLine)
            .map(s::sumPoints)
            .ifPresentOrElse(
                p -> System.out.println("The guide will result in " + p + " points"),
                () -> System.out.println("Bad input")
            );

        System.out.println("Day 2 P2 start: ");
        s.parseInput(s::parseLine2)
            .map(s::sumPoints)
            .ifPresentOrElse(
                p -> System.out.println("The guide will result in " + p + " points"),
                () -> System.out.println("Bad input")
            );
    }

    private int sumPoints(List<Match> matches) {
        return matches.stream()
            .mapToInt(this::totalPoints)
            .sum();
    }

    private record Match(Throw opponent, Throw you) {}

    private int totalPoints(Match match) {
        if (match.you() == match.opponent()) {
            return 3 + match.you().getPoints();
        }
        return match.you().getPoints() + switch (match.you()) {
            case Rock -> match.opponent() == Throw.Scissors ? 6 : 0;
            case Paper -> match.opponent() == Throw.Rock ? 6 : 0;
            case Scissors -> match.opponent() == Throw.Paper ? 6 : 0;
        };
    }

    private Optional<Match> parseLine2(String in) {
        String[] bothThrows = in.split(" +");
        if (bothThrows.length != 2) {
            return Optional.empty();
        }

        Optional<Throw> maybeThrowA = Throw.fromInput(bothThrows[0]);
        if (maybeThrowA.isEmpty()) {
            return Optional.empty();
        }
        Optional<Throw> maybeThrowB = Call.fromInput(bothThrows[1]).map(c -> determineThrow(maybeThrowA.get(), c));

        if(maybeThrowB.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new Match(maybeThrowA.get(), maybeThrowB.get()));
        }
    }

    private Throw determineThrow(Throw opponent, Call call) {
        return switch (call) {
            case Lose -> switch (opponent) {
                case Rock -> Throw.Scissors;
                case Paper -> Throw.Rock;
                case Scissors -> Throw.Paper;
            };
            case Draw -> opponent;
            case Win -> switch (opponent) {
                case Rock -> Throw.Paper;
                case Paper -> Throw.Scissors;
                case Scissors -> Throw.Rock;
            };
        };
    }

    private Optional<Match> parseLine(String in) {
        String[] bothThrows = in.split(" +");
        if (bothThrows.length != 2) {
            return Optional.empty();
        }

        Optional<Throw> maybeThrowA = Throw.fromInput(bothThrows[0]);
        Optional<Throw> maybeThrowB = Throw.fromInput(bothThrows[1]);

        if(maybeThrowA.isEmpty() || maybeThrowB.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new Match(maybeThrowA.get(), maybeThrowB.get()));
        }
    }

    private Optional<List<Match>> parseInput(Function<String, Optional<Match>> parser) {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            List<String> lines = readFromInputStream(inputStream);
            return Optional.of(
                lines.stream()
                    .map(parser)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
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
