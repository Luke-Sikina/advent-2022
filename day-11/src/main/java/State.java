import java.util.Map;

public record State(int concern, Map<Integer, Monkey> monkeys) { }
