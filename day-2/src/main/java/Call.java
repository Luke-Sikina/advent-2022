import java.util.Optional;

public enum Call {
    Lose, Draw, Win;

    static Optional<Call> fromInput(String in) {
        return switch (in) {
            case "X" -> Optional.of(Lose);
            case "Y" -> Optional.of(Draw);
            case "Z" -> Optional.of(Win);
            default -> Optional.empty();
        };
    }
}