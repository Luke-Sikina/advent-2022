import java.util.Optional;

public enum Throw {
    Rock(1), Paper(2), Scissors(3);

    private final int points;

    Throw(int points) {
        this.points = points;
    }

    static Optional<Throw> fromInput(String in) {
        return switch (in) {
            case "A", "X" -> Optional.of(Rock);
            case "B", "Y" -> Optional.of(Paper);
            case "C", "Z" -> Optional.of(Scissors);
            default -> Optional.empty();
        };
    }

    public int getPoints() {
        return points;
    }
}