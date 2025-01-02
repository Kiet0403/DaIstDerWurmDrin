package com.example.daistderwurmdrin;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final int MAX_SCORE = 64;

    // Data Fields
    private com.example.daistderwurmdrin.Die d;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    private List<Player> players;
    private int currentPlayerIndex;
    private Player current;

    private boolean p1checkpoint1 = false;
    private boolean p1checkpoint2 = false;
    private boolean p2checkpoint1 = false;
    private boolean p2checkpoint2 = false;
    private boolean p3checkpoint1 = false;
    private boolean p3checkpoint2 = false;
    private boolean p4checkpoint1 = false;
    private boolean p4checkpoint2 = false;


    // Constructor
    public Game(String p1name, String p2name, String p3name, String p4name) {
        d = new Die();
        players = new ArrayList<>();
        p1 = new Player(p1name);
        p2 = new Player(p2name);
        p3 = new Player(p3name);
        p4 = new Player(p4name);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        currentPlayerIndex = 0;        
        current = players.get(currentPlayerIndex);
    }

    // Accessor methods

    public Die getDie() {
        return d;
    }

    public Player getCurrent() {
        return current;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public Player getP3() {
        return p3;
    }

    public Player getP4() {
        return p4;
    }

    // Status Methods
    public boolean gameOver() {
        return current.getTotalScore() >= MAX_SCORE;
    }

    public boolean p1Turn() {
        return currentPlayerIndex == 0;
    }

    // Game Play Methods
    public void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        current = players.get(currentPlayerIndex);
    }

    public void roll() {
        d.roll();
        int t = d.getTop();
        current.updateTurn(t);
        current.saveScore();
//        if (t == 1) {
//            current.resetTurnScore();
//            switchTurn();
//        }
    }

    public void hold() {
        current.saveScore();
        if (!gameOver()) {
            switchTurn();
            d.setTop(0);
        }
    }

    public void checkProgress() {
        if(!p1checkpoint1 &&
                !p2checkpoint1 &&
                !p3checkpoint1 &&
                !p4checkpoint1) {
            if (p1.getTotalScore() >= 0.3 * MAX_SCORE) {
                System.out.println("Player 1 progress bar reached 30%: true");
                p1checkpoint1 = true;
            }
            if (p2.getTotalScore() >= 0.3 * MAX_SCORE) {
                System.out.println("Player 2 progress bar reached 30%: true");
                p2checkpoint1 = true;
            }
            if(p3.getTotalScore() >= 0.3 * MAX_SCORE){
                System.out.println("Player 3 progress bar reached 30%: true");
                p3checkpoint1 = true;
            }
            if(p4.getTotalScore() >= 0.3 * MAX_SCORE){
                System.out.println("Player 4 progress bar reached 30%: true");
                p4checkpoint1 = true;
            }
        }
        if(!p1checkpoint1 &&
                !p2checkpoint1 &&
                !p3checkpoint1 &&
                !p4checkpoint1) {
            if (p1.getTotalScore() >= 0.7 * MAX_SCORE) {
                System.out.println("Player 1 progress bar reached 70%: true");
                p1checkpoint2 = true;
            }
            if (p2.getTotalScore() >= 0.7 * MAX_SCORE) {
                System.out.println("Player 2 progress bar reached 70%: true");
                p2checkpoint2 = true;
            }
            if(p3.getTotalScore() >= 0.7 * MAX_SCORE){
                System.out.println("Player 3 progress bar reached 70%: true");
                p3checkpoint2 = true;
            }
            if(p4.getTotalScore() >= 0.7 * MAX_SCORE){
                System.out.println("Player 4 progress bar reached 70%: true");
                p4checkpoint2 = true;
            }
        }
    }

//    public void checkBooster(){
//        if (p1checkpoint1 = true && p2checkpoint1 = false){
//
//        }
//    }

    public static void main(String[] args) {
        Game g = new Game("Mark", "Ryan", "Alice", "Ella");
        for (int i = 0; i < 10; i++) {
            g.roll();
            g.roll();
            g.hold();
            System.out.println("Die Rolled " + g.getDie().getTop());
            System.out.println("p1 Turn: " + g.getP1().getTurnScore());
            System.out.println("p1 Total: " + g.getP1().getTotalScore());
            System.out.println("p2 Turn: " + g.getP2().getTurnScore());
            System.out.println("p2 Total: " + g.getP2().getTotalScore());
            System.out.println("p3 Turn: " + g.getP3().getTurnScore());
            System.out.println("p3 Total: " + g.getP3().getTotalScore());
            System.out.println("p4 Turn: " + g.getP4().getTurnScore());
            System.out.println("p4 Total: " + g.getP4().getTotalScore());
        }
    }
}
