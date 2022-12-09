import java.util.Optional;

public enum Direction {
    Left, Right, Up, Down;

    public static Optional<Direction> parse(String raw) {
        return switch (raw.charAt(0)) {
            case 'L' -> Optional.of(Left);
            case 'R' -> Optional.of(Right);
            case 'U' -> Optional.of(Up);
            case 'D' -> Optional.of(Down);
            default -> Optional.empty();
        };
    }
}
