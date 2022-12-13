public record Operation(Value left, Operator operator, Value right) {
    public int calculateConcern(State start) {
        int l = left.value(start);
        int r = right.value(start);

        return switch (operator) {
            case Plus -> l + r;
            case Times -> l * r;
        };
    }
}
