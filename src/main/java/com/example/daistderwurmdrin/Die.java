/** * OOP Java Project

 * Children Board Game Simulation:  Da ist der Wurm drin

 * Link: https://www.amazon.de/Zoch-601132100-Wurm-Kinderspiel-Jahres/dp/B004L87UQO?th=1;
 * https://www.youtube.com/watch?v=kD8JI8RpTFM;

 * @author Van Tuan Kiet Vo - 1589900

 */


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

    public static void main(String[] args) {
        Die d = new Die(6, 1);
        System.out.println(d.getTop());
        for (int i = 0; i < 10; i++) {
            d.roll();
            System.out.println(i + ": " + d.getTop());
        }
    }
}
