import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Dock {
    private Map<Integer, Stack<Character>> places;

    public Dock(Map<Integer, Stack<Character>> places) {
        this.places = places;
    }

    public List<Character> getTopOfEachStack() {
        return places.entrySet().stream()
            .sorted(Comparator.comparingInt(Map.Entry::getKey))
            .map(Map.Entry::getValue)
            .map(Stack::peek)
            .toList();
    }

    public void execute(Move move) {
        for (int i = 0; i < move.num(); i++) {
            Character toMove = places.get(move.from()).pop();
            places.get(move.to()).push(toMove);
        }
    }

    public void executeAllAtOnce(Move move) {
        Stack<Character> tempStack = new Stack<>();
        for (int i = 0; i < move.num(); i++) {
            Character toMove = places.get(move.from()).pop();
            tempStack.push(toMove);
        }

        while (!tempStack.isEmpty()) {
            places.get(move.to()).push(tempStack.pop());
        }
    }
}
