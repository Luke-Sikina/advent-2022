import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class MovesParserTest {

    @Test
    void shouldParse() {
        String raw = """
            move 3 from 9 to 6
            move 7 from 6 to 2
            move 1 from 1 to 5
            
            """;
        List<Move> actual = new MovesParser().parse(Arrays.stream(raw.split("\n")).toList());
        List<Move> expected = List.of(
            new Move(3, 9, 6),
            new Move(7, 6, 2),
            new Move(1, 1, 5)
        );

        Assertions.assertEquals(expected, actual);
    }
}