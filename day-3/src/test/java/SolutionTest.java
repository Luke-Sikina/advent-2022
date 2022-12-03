import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void shouldScore() {
        Assertions.assertEquals(1, new Solution().score('a'));
        Assertions.assertEquals(26, new Solution().score('z'));
        Assertions.assertEquals(27, new Solution().score('A'));
        Assertions.assertEquals(52, new Solution().score('Z'));
    }
}