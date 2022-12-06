import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DockParser implements Parser<Dock> {
    @Override
    public Dock parse(List<String> raw) {
        String base = raw.get(raw.size() - 1);
        List<String> crates = new ArrayList<>(raw.subList(0, raw.size() - 1));
        Collections.reverse(crates);

        // initialize each stack
        Map<Integer, Stack<Character>> dock = Arrays.stream(base.split(" +"))
            .filter(s -> !s.isBlank())
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(Collectors.toMap(Function.identity(), (i) -> new Stack<>()));

        // create a regex for a row of crates:
        // '   [N] [J] [W]     [J] [F] [D] [F]'
        String regex = IntStream.range(1, dock.size() + 1)
            .boxed()
            .map(i -> "((\\[\\w\\] ?)|(    ?))")
            .collect(Collectors.joining());
        Pattern baseRegex = Pattern.compile(regex);

        // for each row of crates, put actual crates in their respective stacks
        for (String crate : crates) {
            Matcher matcher = baseRegex.matcher(crate);
            matcher.matches();
            for (int i = 0; i < dock.size(); i++) {
                char c = matcher.group(1 + i * 3).charAt(1);
                if (c != ' ') {
                    // 0 index to 1 index
                    dock.get(i + 1).push(c);
                }
            }
        }

        return new Dock(dock);
    }
}
