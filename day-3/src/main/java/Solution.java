import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 3 P1");
        s.parseInput()
            .map(s::p1)
            .ifPresentOrElse(
                sum -> System.out.println("The sum of the shared items is " + sum + "."),
                () -> System.out.println("Bad input.")
            );

        System.out.println("Day 3 P2");
        s.parseInput2()
            .map(s::p2)
            .ifPresentOrElse(
                sum -> System.out.println("The sum of the shared items is " + sum + "."),
                () -> System.out.println("Bad input.")
            );
    }

    private int p1(List<Compartment> compartments) {
        return compartments.stream()
            .map(this::diff)
            .mapToInt(this::score)
            .sum();
    }

    private int p2(List<ElfGroup> compartments) {
        return compartments.stream()
            .map(this::commonLetter)
            .mapToInt(this::score)
            .sum();
    }

    private record Compartment(String left, String right){}

    private char diff(Compartment toDiff) {
        Map<Character, Long> counts = toDiff.left()
            .chars()
            .distinct()
            .boxed()
            .collect(Collectors.groupingBy(c -> (char) c.intValue(), Collectors.counting()));

        toDiff.right()
            .chars()
            .distinct()
            .boxed()
            .map(i -> (char)i.intValue())
            .forEach(c -> counts.put(c, counts.getOrDefault(c, 0L) + 1));

        return counts.entrySet().stream()
            .filter(e -> e.getValue() > 1)
            .findFirst()
            .map(Map.Entry::getKey)
            .orElseThrow();
    }


    public int score(Character c) {
        if (c.toString().toLowerCase().equals(c.toString())) {
            return 1 + c - 'a';
        } else {
            return 27 + c - 'A';
        }
    }

    private record ElfGroup(String a, String b, String c) {}

    private char commonLetter(ElfGroup elves) {
        Stream<Integer> allChars = Stream.concat(
            Stream.concat(
                elves.a().chars().distinct().boxed(),
                elves.b().chars().distinct().boxed()
            ),
            elves.c().chars().distinct().boxed()
        );

        return allChars.collect(Collectors.groupingBy(i -> (char)i.intValue(), Collectors.counting()))
            .entrySet().stream()
            .filter(e -> e.getValue() == 3L)
            .findFirst()
            .map(Map.Entry::getKey)
            .orElseThrow();
    }

    private Optional<List<ElfGroup>> parseInput2() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            List<String> raw = readFromInputStream(inputStream);
            List<ElfGroup> elfGroups = new ArrayList<>();
            String a = "", b = "", c = "";
            for (int i = 0; i < raw.size(); i++) {
                if (i > 0 && i % 3 == 0) {
                    elfGroups.add(new ElfGroup(a,b,c));
                }
                switch (i % 3) {
                    case 0 ->  a = raw.get(i);
                    case 1 -> b = raw.get(i);
                    case 2 -> c = raw.get(i);
                }
            }
            elfGroups.add(new ElfGroup(a,b,c));
            return Optional.of(elfGroups);


        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<List<Compartment>> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            return Optional.of(readFromInputStream(inputStream).stream()
                .map(s -> new Compartment(s.substring(0, s.length() / 2), s.substring(s.length()/ 2)))
                .toList()
            );

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
