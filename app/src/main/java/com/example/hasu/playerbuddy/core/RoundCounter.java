package com.example.hasu.playerbuddy.core;

public class RoundCounter {
    private String[] labels = {"Top of ", "Bottom of "};
    private int counter;
    public RoundCounter() { counter = 0; }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(labels[counter % labels.length]);
        sb.append(counter / labels.length + 1);
        return sb.toString();
    }
    public int getValue() { return counter; }
    public void setValue(int i) { counter = i; }
    public RoundCounter inc() { ++counter; return this; }
    public RoundCounter dec() { --counter; return this; }
}
