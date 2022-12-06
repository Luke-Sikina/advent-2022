import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        s.parseInput()
            .map(s::part1)
            .ifPresentOrElse(
                u -> System.out.println("The sequence is: " + u),
                () -> System.out.println("Bad input")
            );
    }

    private int part1(String in) {
        for (int i = 4; i < in.length(); i++) {
            String current = in.substring(i - 4, i);
            if (current.chars().distinct().count() == 4) {
                return i;
            }
        }

        return -1;
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
