public record Test(int check) {
    public boolean test(State state) {
        return state.concern() % check == 0;
    }
}
