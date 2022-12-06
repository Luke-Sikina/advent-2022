
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class DockParserTest {
    @Test
    void shouldParse() {
        String raw  = """
                                [Z] [W] [Z]
                [D] [M]         [L] [P] [G]
            [S] [N] [R]         [S] [F] [N]
            [N] [J] [W]     [J] [F] [D] [F]
        [N] [H] [G] [J]     [H] [Q] [H] [P]
        [V] [J] [T] [F] [H] [Z] [R] [L] [M]
        [C] [M] [C] [D] [F] [T] [P] [S] [S]
        [S] [Z] [M] [T] [P] [C] [D] [C] [D]
         1   2   3   4   5   6   7   8   9
        """;

        Dock actual = new DockParser().parse(Arrays.stream(raw.split("\n")).toList());
        Assertions.assertNotNull(actual);
    }
}