import java.util.List;

public record Monkey(int id, List<Integer> items, Operation operation, Test test, int trueMonkeyId, int falseMonkeyId) {
}
