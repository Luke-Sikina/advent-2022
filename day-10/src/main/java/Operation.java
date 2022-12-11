import java.util.function.Function;

public abstract sealed interface Operation permits Operation.Add, Operation.NoOp {
    public abstract int clockCycles();

    public abstract int updateState(int state);

    public record Add(int clockCycles, int toAdd) implements Operation {
        public Add(int toAdd) {
            this(2, toAdd);
        }

        @Override
        public int updateState(int state) {
            return state + toAdd;
        }
    }

    public record NoOp(int clockCycles) implements Operation {
        public NoOp() {
            this(1);
        }

        @Override
        public int updateState(int state) {
            return state;
        }
    }
}
