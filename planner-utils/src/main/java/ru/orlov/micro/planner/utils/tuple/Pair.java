package ru.orlov.micro.planner.utils.tuple;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair() {
    }

    public Pair(final F first,
                final S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(final F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(final S second) {
        this.second = second;
    }
}