package com.example.daistderwurmdrin;

public class Player {

    // Data Fields
    private String name;
    private int turnScore;
    private int totalScore;
    private int totalBoosters;
    private boolean[] usedCheckpoints;

    // Constructor
    public Player(String name) {
        this.name = name;
        turnScore = 0;
        totalScore = 0;
        totalBoosters = 2;
        usedCheckpoints = new boolean[3];
    }

    // Accessor Methods
    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public int getBoosters() {
        return totalBoosters;
    }

    // Game Play Methods
    public void resetTurnScore() {
        turnScore = 0;
    }

    public void useBooster() {
        if (totalBoosters > 0){
            totalBoosters--;
        }
    }

    public boolean hasBoosters(){
        return totalBoosters > 0;
    }

    public void usedCheckpoint(String checkpoint) {
        int ckpt = Integer.parseInt(checkpoint);
        usedCheckpoints[ckpt] = true;
    }

    public boolean hasUsedCheckpoint(String checkpoint) {
        int ckpt = Integer.parseInt(checkpoint);
        return usedCheckpoints[ckpt];
    }

    public void updateTurn(int roll) {
        turnScore += roll;
    }

    public void saveScore() {
        totalScore += turnScore;
        resetTurnScore();
    }

    public void addBonusPoints(int points) {
        totalScore = totalScore + points;
    }

    public static void main(String[] args) {
        Die d = new Die(6, 1);
        Player p = new Player("Mark");
        System.out.println("Turn Score: " + p.getTurnScore());
        System.out.println("Total Score: " + p.getTotalScore());
        System.out.println("Rolling...");
        for (int i = 0; i < 10; i++) {
            d.roll();
            p.updateTurn(d.getTop());
            System.out.println("Turn Score: " + p.getTurnScore());
            System.out.println("Total Score: " + p.getTotalScore());
        }
        p.saveScore();
        System.out.println("Saving...");
        System.out.println("Turn Score: " + p.getTurnScore());
        System.out.println("Total Score: " + p.getTotalScore());
    }
}

class HumanPlayer extends Player{
    public HumanPlayer(String name) {
        super(name);
    }
}

class Bot extends Player {
    public String difficulty;
    Bot(String difficulty, String name) {
        super(name);
        this.difficulty=difficulty;
    }
}