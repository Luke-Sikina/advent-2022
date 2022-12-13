public sealed interface Value permits Value.Constant, Value.Old {
    int value(State state);

    record Constant(int val) implements Value {
        @Override
        public int value(State state) {
            return val;
        }
    }

    record Old() implements Value {

        @Override
        public int value(State state) {
            return state.concern();
        }
    }
}


