package com.example.daistderwurmdrin;

public class Die {

    // Private Data Fields
    private int sides;
    private int top;

    // Constructor
    public Die(int sides, int top) {
        this.sides = sides;
        this.top = top;
    }

    public Die() {
        sides = 6;
        top = 0;
    }

    // Accessor Methods
    public int getTop() {
        return top;
    }

    // Mutator Methods
    public void setTop(int top) {
        if (top >= 0 && top <= sides) {
            this.top = top;
        }
    }

    // Game Play Methods
    public void roll() {
        top = 1 + (int)(Math.random() * sides);
    }

}
