import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Solution {

    private State iterate(State start) {
        State current = start;
        List<Monkey> monkeys = start.monkeys().entrySet().stream()
            .sorted(Comparator.comparingInt(Map.Entry::getKey))
            .map(Map.Entry::getValue)
            .toList();

        for (Monkey monkey : monkeys) {
            int concern = monkey.operation().calculateConcern(current);
            current = new State(concern, current.monkeys());
            if (monkey.test().test(current)) {

            }
        }

        return current;

    }
}
