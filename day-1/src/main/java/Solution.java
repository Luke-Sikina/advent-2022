import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Solution {

    public static void main(String[] args) {
        Solution soln = new Solution();
        soln.readInput()
            .map(soln::findMaxCal)
            .ifPresentOrElse(System.out::println, () -> System.out.println("bad input"));
    }

    public Integer findMaxCal(List<List<Integer>> elfCalories) {
        return elfCalories.stream()
            .mapToInt(elf -> elf.stream().mapToInt(i -> i).sum())
            .max()
            .orElse(0);
    }

    public Optional<List<List<Integer>>> readInput() {
        try (InputStream inputStream = Solution.class.getResourceAsStream("calories.txt")) {
            List<String> lines = readFromInputStream(inputStream);
            List<List<Integer>> parsedInput = new ArrayList<>();
            List<Integer> currentElf = new ArrayList<>();
            for (String line : lines) {
                if (line.isBlank()) {
                    parsedInput.add(currentElf);
                    currentElf = new ArrayList<>();
                } else {
                    currentElf.add(Integer.parseInt(line));
                }
            }

            return Optional.of(parsedInput);
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
