import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovesParser implements Parser<List<Move>> {
    private static final Pattern moveRegex = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    @Override
    public List<Move> parse(List<String> raw) {
        return raw.stream()
            .map(moveRegex::matcher)
            .filter(Matcher::matches)
            .map(m -> new Move(Integer.parseInt(m.group(1)),Integer.parseInt(m.group(2)),Integer.parseInt(m.group(3))))
            .toList();
    }
}
