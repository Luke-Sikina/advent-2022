import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Day 6 Part 1");
        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(
                u -> System.out.println("The sequence is: " + u),
                () -> System.out.println("Bad input")
            );

        System.out.println("Day 6 Part 2");
        s.parseInput()
            .map(s::part2)
            .ifPresentOrElse(
                u -> System.out.println("The sequence is: " + u),
                () -> System.out.println("Bad input")
            );
    }
    private int part2(String in) {
        return getFirstUniqueSeq(in, 14).orElse(-1);
    }

    private Optional<Integer> getFirstUniqueSeq(String in, int len) {
        for (int i = len; i < in.length(); i++) {
            String current = in.substring(i - len, i);
            if (current.chars().distinct().count() == len) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }


    private int part1(String in) {
        return getFirstUniqueSeq(in, 4).orElse(-1);
    }

    private Optional<String> parseInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("input.txt")) {
            String raw = readFromInputStream(inputStream);

            return Optional.of(raw);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.readLine();
        }
    }
}
