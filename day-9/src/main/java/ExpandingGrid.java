import java.util.Set;

public record ExpandingGrid(Coordinate head, Coordinate tail, Set<Coordinate> tailLocations) {
    private ExpandingGrid move(Direction direction) {
        Coordinate newHead = switch (direction) {
            case Left -> new Coordinate(head.x() - 1, head.y());
            case Right -> new Coordinate(head.x() + 1, head.y());
            case Up -> new Coordinate(head.x(), head.y() + 1);
            case Down -> new Coordinate(head.x(), head.y() - 1);
        };

        Coordinate newTail = moveTail(newHead, tail);
        tailLocations.add(newTail);

        return new ExpandingGrid(newHead, newTail, tailLocations);
    }

    public ExpandingGrid move(Direction direction, int count) {
        ExpandingGrid current = this;
        for (int i = 0; i < count; i++) {
            current = current.move(direction);
        }

        return current;
    }

    private Coordinate moveTail(Coordinate h, Coordinate t) {
        if (h.x() + 1 < t.x()) {
            return new Coordinate(t.x() - 1, t.y());
        }
        if (h.x() - 1 > t.x()) {
            return new Coordinate(t.x() + 1, t.y());
        }
        if (h.y() + 1 < t.y()) {
            return new Coordinate(t.x(), t.y() - 1);
        }
        if (h.y() - 1 > t.y()) {
            return new Coordinate(t.x(), t.y() + 1);
        }

        return new Coordinate(t.x(), t.y());
    }
}
